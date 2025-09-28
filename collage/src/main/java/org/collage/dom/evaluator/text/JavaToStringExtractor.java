package org.collage.dom.evaluator.text;

import java.io.IOException;
import org.collage.dom.ast.IJavaCV;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class JavaToStringExtractor implements ICommand {
    @Override
    public void execute() {
        var java = javaCV.getJava();
        var s = "<?java" + java.getValue() + "?>";
        if (stringHandlerCV.getString() != null) {
            stringHandlerCV.setString(s);
        }
        var writer = evaluationCV.getWriter();
        if (writer != null) {
            try {
                writer.write("<?java");
                //			writer.write(java.getStream()); // not existing yet!
                writer.write(java.getValue());
                writer.write("?>");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
}
