package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.TextNode;
import org.collage.dom.evaluator.EvaluationCV;
import org.xcommand.core.IXCommand;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class TextNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		TextNode node = (TextNode) EvaluationCV.getNode(aCtx);
		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		String ss = decodedString("\t_writer.write(#") + node.getValue() + decodedString("#);\n");
		methodBody.append(ss);
	}

	private String decodedString(String aString)
	{
		return StringUtils.replace(aString, "#", "\"");
	}
}
