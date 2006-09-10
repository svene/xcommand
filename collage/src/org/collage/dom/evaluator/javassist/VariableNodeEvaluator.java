package org.collage.dom.evaluator.javassist;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.collage.dom.evaluator.text.TextTemplate;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.template.Template;

import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class VariableNodeEvaluator implements IXCommand
{

	public VariableNodeEvaluator()
	{
		try
		{
			InputStream is = new FileInputStream(new File("javassist_var.txt"));
			Map ctx = new HashMap();
			DomNodeCreationHandlerContextView.setProduceJavaSource(ctx, Boolean.FALSE);
			template = new TextTemplate(is, ctx);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void execute(Map aCtx)
	{
		Map ctx = new HashMap();
		VariableNode node = (VariableNode) EvaluationContextView.getNode(aCtx);
		String vn = node.getVariableName();
		ctx.put("varName", vn);
		String ss = template.getStringResult(ctx);

		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		methodBody.append(ss);
	}

	private Template template;
}
