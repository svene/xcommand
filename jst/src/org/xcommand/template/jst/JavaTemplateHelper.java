package org.xcommand.template.jst;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class JavaTemplateHelper
{

	public JavaTemplateHelper(Map aCtx)
	{
		init(aCtx);
	}
	public JavaTemplateHelper(Writer aWriter, Map aCtx)
	{
		aCtx.put("writer", aWriter);
		init(aCtx);
	}
	private void init(Map aCtx)
	{
		ctx = aCtx;
		Writer writer = (Writer) aCtx.get("writer");
		if (writer == null)
		{
			writerAdapter = new SystemOutWriterAdapter();
		}
		else
		{
			writerAdapter = new WriterWriterAdapter(writer);
		}
	}

	public void $s(String aString)
	{
		if (aString == null) throw new IllegalArgumentException("aString == null");
		writeToWriter(aString);
	}
	public void $v(String aName)
	{
		if (aName == null) throw new IllegalArgumentException("aName == null");
		String value = (String) ctx.get(aName);
		if (value == null) throw new IllegalArgumentException("unknown property '" + aName + "'");
		writeToWriter(value);
	}

	private void writeToWriter(String aString)
	{
		writerAdapter.write(aString);
	}

	private class SystemOutWriterAdapter implements IWriterAdapter
	{
		public void write(String aString)
		{
			System.out.print(aString);
		}
	}
	private class WriterWriterAdapter implements IWriterAdapter
	{
		public WriterWriterAdapter(Writer aWriter)
		{
			writer = aWriter;
		}
		public void write(String aString)
		{
			try
			{
				writer.write(aString);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		private Writer writer;
	}

	private IWriterAdapter writerAdapter;
	private Map ctx;
}
