package org.xcommand.template.jst;

import java.util.Map;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class DefaultJSTRoutines implements IJSTRoutines
{
	public void ensureExistenceOfWriter(Map aCtx)
	{
		Writer writer = (Writer) aCtx.get("writer");
		if (writer == null)
		{
			writer = new OutputStreamWriter(System.out);
			aCtx.put("writer", writer);
		}
	}
	public void $s(Map aCtx, String aString)
	{
		if (aCtx == null) throw new IllegalArgumentException("aString == null");
		if (aString == null) throw new IllegalArgumentException("aString == null");
		writeToWriter(aCtx, aString);
	}

	public void $v(Map aCtx, String aName)
	{
		if (aName == null) throw new IllegalArgumentException("aName == null");
		String value = (String) aCtx.get(aName);
		if (value == null) throw new IllegalArgumentException("unknown property '" + aName + "'");
		writeToWriter(aCtx, value);
	}
	private void writeToWriter(Map aCtx, String aString)
	{
		Writer writer = (Writer) aCtx.get("writer");
//		if (writer == null)
//		{
//			writer = new OutputStreamWriter(System.out);
//			aCtx.put("writer", writer);
//		}
		try
		{
			writer.write(aString);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
