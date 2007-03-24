package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.evaluator.EvaluationCV;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class JavaNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		JavaNode node = (JavaNode) EvaluationCV.getNode(aCtx);
		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
//		System.out.println("*** javacode: '" + node.getValue() + "'");
		String ss = "\t" + node.getValue() + "\n";
		methodBody.append(ss);
	}

	private String decodedString(String aString)
	{
		return StringUtils.replace(aString, "#", "\"");
	}
}
