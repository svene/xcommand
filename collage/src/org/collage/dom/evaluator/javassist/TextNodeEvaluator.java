package org.collage.dom.evaluator.javassist;

import org.collage.dom.ast.TextNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class TextNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		TextNode node = (TextNode) EvaluationContextView.getNode(aCtx);
		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		String ss = decodedString("\t_writer.write(#") + node.getValue() + decodedString("#);\n");
		methodBody.append(ss);
	}

	private String decodedString(String aString)
	{
		return StringUtils.replace(aString, "#", "\"");
	}
}
