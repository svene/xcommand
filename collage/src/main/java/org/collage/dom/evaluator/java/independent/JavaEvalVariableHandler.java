package org.collage.dom.evaluator.java.independent;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.*;

import java.io.InputStream;
import java.util.HashMap;

public class JavaEvalVariableHandler implements ICommand
{
	public JavaEvalVariableHandler()
	{
		try
		{
			String resourceLocation = "org/collage/dom/evaluator/java/javassist/javassist_var.txt";
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceLocation);
			if (is == null) throw new RuntimeException("is == null");
			TCP.pushContext(new HashMap());
			domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);

			templateCommand = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(is));
			TCP.popContext();
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

	private ICommand templateCommand;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
