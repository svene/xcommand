package org.xcommand.template.jst;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

import java.io.*;

/** @deprecated replaced by JavaCC file 'jst.jj' */
public class TemplateToSourceGenerator implements ICommand
{

	public void execute()
	{
		InputStream is = (InputStream) TCP.getContext().get("inputstream");
		InputStreamReader isr = new InputStreamReader(is);
		String classname = (String) TCP.getContext().get("classname");

//		File f = new File("jtemplates/JTemplate1.java");
		Writer writer = new StringWriter();
		LineIterator it = null;
		try
		{

//			it = FileUtils.lineIterator(f, "UTF-8");
			it = new LineIterator(isr);
			boolean insideText = false;
			while (it.hasNext())
			{
				String line = it.nextLine();
				int p = 0;
				int n = line.length();
				while (p < n)
				{

					int idx;
					if (!insideText)
					{
						// Line text:
						idx = line.indexOf(TPL_LINE, p);
						if (idx != -1)
						{
							writeFromTo(writer, line, p, idx);
							String s = line.substring(idx + TPL_LINE.length(), n) + "\\n";
							writeText(writer, s);

							write(writer, "\n");
							p = n;
							continue;
						}
						// Begin text:
						idx = line.indexOf(TPL_START, p);
						if (idx != -1)
						{
							writeFromTo(writer, line, p, idx);
							insideText = true;
							p = idx + TPL_START.length();
							if (p >= n)
							{
								writeText(writer, "\\n");
								write(writer, "\n");
							}
							continue;
						}
					}
					else
					{
						int idx2 = line.indexOf(TPL_END, p);
						boolean foundEnd = idx2 != -1;
						if (foundEnd)
						{
							String s = line.substring(p, idx2);

							insideText = false;
							p = idx2 + TPL_END.length();
							if (p >= n)
							{
								s = s + "\\n";
							}
							writeText(writer, s);
							continue;
						}
						else // block wraps over to next line:
						{
							String s = line.substring(p, n) + "\\n";
							writeText(writer, s);

							write(writer, "\n");
							p = n;
							continue;
						}

					}

					// Normal Java code:
					writeFromTo(writer, line, p, n);
					p = n;
				}
				// EOL reached:
				if (insideText) // block wraps over to next line:
				{
					// do nothing
				}
				else
				{
					// Skip empty lines outside of text blocks:
					if (line.length() == 0) {
						continue;
					}
					// Write a newline for Java code EOL:
					writer.write("\n");
				}
			}
			writer.flush();
			String source = writer.toString();
			TCP.getContext().put("source", source);

			// Write source code as file to disk:
			String gensourcedir = (String) TCP.getContext().get("gensourcedir");
			if (gensourcedir != null)
			{
				String dirName = gensourcedir + "/";
				File dir = new File(gensourcedir);
				System.out.println("gensrcdir.path=" + dir.getAbsolutePath());
				File rf = new File(gensourcedir + "/" + classname + ".java");
				rf.createNewFile();
				FileUtils.writeStringToFile(rf, source);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if (it != null)
			{
				LineIterator.closeQuietly(it);
			}
		}
	}

	private void writeText(Writer writer, String aString)
	{
		try
		{
			String s = aString.replaceAll("\\\"", "\\\"");
//			s = aString.replaceAll("\\$s\\((.*?)\\)", "\");\\$s($1);\\$s(");
			writer.write(WRITE_CMD + "(\"" + s + "\");");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void writeTextFromTo(Writer aWriter, String aLine, int aFrom, int aTo)
	{
		String extract = aLine.substring(aFrom, aTo);
		writeText(aWriter, extract);
	}

	private void writeFromTo(Writer aWriter, String aLine, int aFrom, int aTo)
	{
		if (aTo > aFrom)
		{
			write(aWriter, aLine.substring(aFrom, aTo));
		}
	}

	private void write(Writer aWriter, String aString)
	{
		writeToWriter(aWriter, aString);
	}
	private void writeToWriter(Writer aWriter, String aString)
	{
		try
		{
			aWriter.write(aString);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	public static final String TPL_LINE = "//#";
	public static final String TPL_START = "/*#";
	public static final String TPL_END = "#*/";
//	public static final String WRITE_CMD = "_writer.write";
//	public static final String WRITE_CMD = "th.$s";
	public static final String WRITE_CMD = "$s";

//	private static final String CLASSNAME = "org/example/svenehrke/janino/command/MyCommand";
//	private static final String CLASSNAME = "JTemplate1T";
}
