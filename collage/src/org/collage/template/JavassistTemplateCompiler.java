package org.collage.template;

import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JavassistTemplateCompiler
{

	public IXCommand newTemplateCommandFromString(String aString)
	{
		return newTemplateCommand(new TemplateSource(aString));
	}

	public IXCommand newTemplateCommand(TemplateSource aTemplateSource)
	{
		// Compile template:
		Map ctx = new HashMap();
		ctx.putAll(TemplateCompiler.getConfigCtx());
		ctx.putAll(aTemplateSource.getContext());
		new DefaultDomNodeCreationHandlerInitializer().execute(ctx);

		ParserCV.setInputStream(ctx, aTemplateSource.getInputStream());
		new TemplateCompiler().execute(ctx);
		// Now we have the root node: `TreeNodeCV.getTreeNode(ctx)'
		// Use String based text evaluation. Since this is only for template compilation and not template usage
		// it is not performance/memory relevant and thus OK: 
		StringHandlerCV.setString(ctx, "");
		new JavassistTraverser().execute(ctx);
		// Return dynamically (by javassist) created template command (IXCommand)
		return JavaTemplateCmdCV.getTemplateComand(ctx);
	}

	public IXCommand newTemplateCommandFromStream(InputStream aInputStream)
	{
		return newTemplateCommand(new TemplateSource(aInputStream));
	}

}
