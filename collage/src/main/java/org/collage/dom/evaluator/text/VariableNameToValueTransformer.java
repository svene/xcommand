package org.collage.dom.evaluator.text;

import java.io.IOException;
import java.io.Writer;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;

public class VariableNameToValueTransformer implements ICommand {
    @Override
    public void execute() {

        var variableName = stringHandlerCV.getString();
        String result;
        var obj = TCP.getContext().get(variableName);
        if (obj != null) {
            result = obj.toString();
        } else {
            result = "${" + variableName + '}';
        }
        if (stringHandlerCV.getString() != null) {
            stringHandlerCV.setString(result);
        }

        Writer writer = evaluationCV.getWriter();
        if (writer != null) {
            try {
                //				writer.write(variable.getStream()); // not existing yet!
                writer.write(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
