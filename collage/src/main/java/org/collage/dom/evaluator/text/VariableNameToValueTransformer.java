package org.collage.dom.evaluator.text;

import java.io.IOException;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;

public class VariableNameToValueTransformer implements ICommand {
    @Override
    public void execute() {
        var variableName = stringHandlerCV.getString().orElseThrow();
        var result =
                switch (TCP.getContext().get(variableName)) {
                    case Object obj -> obj.toString();
                    case null -> "${" + variableName + '}';
                };
        stringHandlerCV.setString(result);
        evaluationCV.getWriter().ifPresent(w -> {
            try {
                w.write(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
