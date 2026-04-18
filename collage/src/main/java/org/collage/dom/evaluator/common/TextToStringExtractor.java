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
        var text = textCV.getText();
        if (stringHandlerCV.getString().isPresent()) {
            stringHandlerCV.setString(text.value());
        }
        evaluationCV.getWriter().ifPresent(w -> {
            try {
                w.write(text.value());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if (stringHandlerCV.getString().isEmpty() && evaluationCV.getWriter().isEmpty()) {
            throw new IllegalStateException(
                    "StringHandlerCV.getString(aCtx) == null && EvaluationCV.getWriter(aCtx) == null");
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
    ITextCV textCV = dbp.newBeanForInterface(ITextCV.class);
}
