package org.collage.dom.evaluator.java.independent;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.javassist.IMethodBodyCV;
import org.xcommand.core.*;

public class JavaEvalTextHandler implements ICommand {
    @Override
    public void execute() {
        var s = stringHandlerCV.getString();
        var ss = "\t_writer.write(\"%s\");\n".formatted(s);
        methodBodyCV.getMethodBody().append(ss);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IMethodBodyCV methodBodyCV = dbp.newBeanForInterface(IMethodBodyCV.class);
}
