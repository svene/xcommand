package org.collage.dom.evaluator.java.independent;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.*;
import org.xcommand.util.ResourceUtil;

import java.io.InputStream;
import java.util.HashMap;

public class JavaEvalVariableHandler implements ICommand
{
	public JavaEvalVariableHandler()
	{
		try
		{
			InputStream is = ResourceUtil.newInputStreamFromResourceLocation("org/collage/dom/evaluator/java/javassist/javassist_var.txt");
			TCP.pushContext(new HashMap());
			domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);

			templateCommand = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(is));
			TCP.popContext();
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void execute()
	{
		String vn = stringHandlerCV.getString();
		TCP.pushContext(new HashMap());
		TCP.getContext().put("varName", vn);
		templateCommand.execute();
		String ss = stringHandlerCV.getString();
		TCP.popContext();

		StringBuffer methodBody = (StringBuffer) TCP.getContext().get("methodbody");
		methodBody.append(ss);
	}

	private final ICommand templateCommand;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
