package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.IJavaCV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.*;

public class JavaEvalJavaHandler implements ICommand {

    private static final Logger log = LoggerFactory.getLogger(JavaEvalJavaHandler.class);

    @Override
    public void execute() {
        var java = javaCV.getJava();
        var s = java.value();

        var methodBody = (StringBuffer) TCP.getContext().get("methodbody");
        log.debug("*** javacode: '{}'", s);
        var ss = "\t" + s + "\n";
        methodBody.append(ss);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}
