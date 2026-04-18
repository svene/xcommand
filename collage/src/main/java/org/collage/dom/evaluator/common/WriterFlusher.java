package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class WriterFlusher implements ICommand {
    @Override
    public void execute() {
        try {
            evaluationCV.getWriter().orElseThrow().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
