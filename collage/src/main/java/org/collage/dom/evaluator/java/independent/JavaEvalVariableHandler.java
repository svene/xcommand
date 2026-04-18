package org.collage.dom.evaluator.java.independent;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.javassist.IMethodBodyCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.*;

public class JavaEvalVariableHandler implements ICommand {
    public JavaEvalVariableHandler() {
        templateCommand = TCP.get(() -> {
            domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
            return new TextTemplateCompiler().newTemplateCommand(new TemplateSource(javassistVar));
        });
    }

    @Override
    public void execute() {
        String vn = stringHandlerCV.getString().orElseThrow();
        String ss = TCP.get(() -> {
            TCP.getContext().put("varName", vn);
            templateCommand.execute();
            return stringHandlerCV.getString().orElseThrow();
        });
        methodBodyCV.getMethodBody().append(ss);
    }

    private final ICommand templateCommand;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IMethodBodyCV methodBodyCV = dbp.newBeanForInterface(IMethodBodyCV.class);

    private final String javassistVar = """
		appendVar(org.xcommand.core.TCP.getContext(), "${varName}",  _writer);
		""";
}
