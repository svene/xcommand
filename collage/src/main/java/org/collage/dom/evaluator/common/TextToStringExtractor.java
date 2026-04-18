package org.collage.dom.evaluator.common;

import java.io.IOException;
import org.collage.dom.ast.ITextCV;
import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

/**
 * Read value of Text via TextCV.getText() and put it on aCtx via `StringHandlerCV.setString'
 */
public class TextToStringExtractor implements ICommand {
    @Override
    public void execute() {
        if (!IStringHandlerCV.hasString() && !IEvaluationCV.hasWriter()) {
            throw new IllegalStateException(
                    "StringHandlerCV.getString(aCtx) == null && EvaluationCV.getWriter(aCtx) == null");
        }
        var text = textCV.getText();
        if (IStringHandlerCV.hasString()) {
            stringHandlerCV.setString(text.value());
        }
        if (IEvaluationCV.hasWriter()) {
            try {
                evaluationCV.getWriter().write(text.value());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
    ITextCV textCV = dbp.newBeanForInterface(ITextCV.class);
}
