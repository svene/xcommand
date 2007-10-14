package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;
import org.collage.dom.evaluator.EvaluationCV;

import java.util.Map;
import java.io.Writer;

public class WriterFlusher implements IXCommand
{
	public void execute(Map aCtx)
	{
		Writer w = EvaluationCV.getWriter(aCtx);
		try
		{
			w.flush();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
