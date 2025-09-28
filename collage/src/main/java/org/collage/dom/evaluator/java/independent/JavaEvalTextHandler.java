package org.collage.dom.evaluator.java.independent;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;

public class JavaEvalTextHandler implements ICommand {
    @Override
    public void execute() {
        // TODO: improve this:
        var methodBody = (StringBuffer) TCP.getContext().get("methodbody");
        var s = stringHandlerCV.getString();
        var ss = decodedString("\t_writer.write(#") + s + decodedString("#);\n");
        methodBody.append(ss);
    }

    private String decodedString(String aString) {
        return aString.replace("#", "\"");
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
