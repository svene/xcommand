package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

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
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
