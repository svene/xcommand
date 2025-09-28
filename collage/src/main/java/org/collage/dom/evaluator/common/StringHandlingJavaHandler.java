package org.collage.dom.evaluator.common;

import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class StringHandlingJavaHandler extends StringHandlingHandler {

    public StringHandlingJavaHandler(StringHandlerCommand aStringHandlerCommand) {
        super(aStringHandlerCommand);
    }

    @Override
    protected String getOriginalText() {
        return javaCV.getJava().getValue();
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}
