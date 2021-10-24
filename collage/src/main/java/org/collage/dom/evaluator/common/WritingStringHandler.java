package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class WritingStringHandler implements IStringHandler
{

	public void handleString(Map<String, Object> aCtx, String aString)
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
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
