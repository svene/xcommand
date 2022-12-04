package org.collage.dom.evaluator.java.independent;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.*;
import org.xcommand.util.ResourceUtil;

public class JavaEvalVariableHandler implements ICommand {
	public JavaEvalVariableHandler() {
		templateCommand = TCP.get(() -> {
			var is = ResourceUtil.newInputStreamFromResourceLocation("org/collage/dom/evaluator/java/javassist/javassist_var.txt");
			domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
			return new TextTemplateCompiler().newTemplateCommand(new TemplateSource(is));
		});
	}

	@Override
	public void execute() {
		var vn = stringHandlerCV.getString();
		var ss = TCP.get(() -> {
			TCP.getContext().put("varName", vn);
			templateCommand.execute();
			return stringHandlerCV.getString();
		});

		var methodBody = (StringBuffer) TCP.getContext().get("methodbody");
		methodBody.append(ss);
	}

	private final ICommand templateCommand;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
