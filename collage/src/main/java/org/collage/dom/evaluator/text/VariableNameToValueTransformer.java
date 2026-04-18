package org.collage.dom.evaluator.text;

import java.io.IOException;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;

public class VariableNameToValueTransformer implements ICommand {
    @Override
    public void execute() {
        var variableName = stringHandlerCV.getString();
        var result =
                switch (TCP.getContext().get(variableName)) {
                    case Object obj -> obj.toString();
                    case null -> "${" + variableName + '}';
                };
        stringHandlerCV.setString(result);
        if (evaluationCV.hasWriter()) {
            try {
                evaluationCV.getWriter().write(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
