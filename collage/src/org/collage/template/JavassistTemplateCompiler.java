package org.collage.template;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

import java.io.InputStream;
import java.util.HashMap;

public class JavassistTemplateCompiler
{

	public ICommand newTemplateCommandFromString(String aString)
	{
		return newTemplateCommand(new TemplateSource(aString));
	}

	public ICommand newTemplateCommand(TemplateSource aTemplateSource)
	{
		// Compile template:
		TCP.pushContext(new HashMap());
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.TRUE);
//!!		ctx.putAll(aTemplateSource.getContext());
		new DefaultDomNodeCreationHandlerInitializer().execute();

		parserCV.setInputStream(aTemplateSource.getInputStream());
		new TemplateCompiler().execute();
		// Now we have the root node: `TreeNodeCV.getTreeNode(ctx)'
		// Use String based text evaluation. Since this is only for template compilation and not template usage
		// it is not performance/memory relevant and thus OK: 
		stringHandlerCV.setString("");
		new JavassistTraverser().execute();
		// Return dynamically (by javassist) created template command (ICommand)
		ICommand tplCmd = javaTemplateCmdCV.getTemplateComand();
		TCP.popContext();
		return tplCmd;
	}

	public ICommand newTemplateCommandFromStream(InputStream aInputStream)
	{
		return newTemplateCommand(new TemplateSource(aInputStream));
	}

	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	IJavaTemplateCmdCV javaTemplateCmdCV = (IJavaTemplateCmdCV) dbp.getBeanForInterface(IJavaTemplateCmdCV.class);
}
