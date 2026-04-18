package org.collage.dom.evaluator.java.independent;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.javassist.IMethodBodyCV;
import org.xcommand.core.*;

public class JavaEvalTextHandler implements ICommand {
    @Override
    public void execute() {
        var s = stringHandlerCV.getString();
        var ss = decodedString("\t_writer.write(#") + s + decodedString("#);\n");
        methodBodyCV.getMethodBody().append(ss);
    }

    private String decodedString(String aString) {
        return aString.replace("#", "\"");
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IMethodBodyCV methodBodyCV = dbp.newBeanForInterface(IMethodBodyCV.class);
}
