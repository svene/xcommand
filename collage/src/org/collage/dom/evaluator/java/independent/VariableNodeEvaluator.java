package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.text.TextTemplate;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.template.Template;
import org.collage.template.TemplateSource;

import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class VariableNodeEvaluator implements IXCommand
{

	public VariableNodeEvaluator()
	{
		try
		{
			String resourceLocation = "org/collage/dom/evaluator/java/javassist/javassist_var.txt";
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceLocation);
			Map ctx = new HashMap();
			DomNodeCreationHandlerCV.setProduceJavaSource(ctx, Boolean.FALSE);
			template = new TextTemplate(new TemplateSource(is, ctx));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void execute(Map aCtx)
	{
		Map ctx = new HashMap();
		VariableNode node = (VariableNode) EvaluationCV.getNode(aCtx);
		String vn = node.getVariableName();
		ctx.put("varName", vn);
		String ss = template.getStringResult(ctx);

		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		methodBody.append(ss);
	}

	private Template template;
}
