package org.collage.dom.evaluator.common;

import java.io.IOException;
import java.util.Map;
import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class WritingStringHandler implements IStringHandler {

    @Override
    public void handleString(Map<String, Object> aCtx, String aString) {
        var writer = evaluationCV.getWriter();
        try {
            writer.write(aString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
