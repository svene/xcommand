package org.collage.dom.evaluator.java.independent;

import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.IXCommand;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JavaEvalVariableHandler implements IXCommand
{
	public JavaEvalVariableHandler()
	{
		try
		{
			String resourceLocation = "org/collage/dom/evaluator/java/javassist/javassist_var.txt";
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceLocation);
			Map ctx = new HashMap();
			DomNodeCreationHandlerCV.setProduceJavaSource(ctx, Boolean.FALSE);

			templateCommand = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(is, ctx));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void execute(Map aCtx)
	{
		String vn = StringHandlerCV.getString(aCtx);

		Map ctx = new HashMap();
		ctx.put("varName", vn);
		templateCommand.execute(ctx);
		String ss = StringHandlerCV.getString(ctx);

		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		methodBody.append(ss);
	}

	private IXCommand  templateCommand;
}
