package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.io.Writer;

public class WriterFlusher implements ICommand {
	@Override
	public void execute() {
		var w = evaluationCV.getWriter();
		try {
			w.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
