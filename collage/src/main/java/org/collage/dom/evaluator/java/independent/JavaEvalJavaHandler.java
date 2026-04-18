package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.IJavaCV;
import org.collage.dom.evaluator.java.javassist.IMethodBodyCV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.*;

public class JavaEvalJavaHandler implements ICommand {

    private static final Logger log = LoggerFactory.getLogger(JavaEvalJavaHandler.class);

    @Override
    public void execute() {
        var java = javaCV.getJava();
        var s = java.value();
        log.debug("*** javacode: '{}'", s);
        var ss = "\t%s\n".formatted(s);
        methodBodyCV.getMethodBody().append(ss);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
    IMethodBodyCV methodBodyCV = dbp.newBeanForInterface(IMethodBodyCV.class);
}
