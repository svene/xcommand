package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

import java.io.Writer;

public class WriterFlusher implements ICommand
{
	public void execute()
	{
		Writer w = evaluationCV.getWriter();
		try
		{
			w.flush();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.getBeanForInterface(IEvaluationCV.class);
}
