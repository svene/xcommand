package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.DynaBeanProvider;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class WritingStringHandler implements IStringHandler
{

	public void handleString(Map aCtx, String aString)
	{
		Writer writer = evaluationCV.getWriter();
		try
		{
			writer.write(aString);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.getBeanForInterface(IEvaluationCV.class);
}
