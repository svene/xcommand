package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.EvaluationCV;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class WritingStringHandler implements IStringHandler
{

	public void handleString(Map aCtx, String aString)
	{
		Writer writer = EvaluationCV.getWriter(aCtx);
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
