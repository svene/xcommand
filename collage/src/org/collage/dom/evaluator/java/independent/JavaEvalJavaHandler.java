package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.Java;
import org.collage.dom.ast.JavaCV;

import java.util.Map;

public class JavaEvalJavaHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		Java java = JavaCV.getJava(aCtx);
		String s = java.getValue();

		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
//		System.out.println("*** javacode: '" + s + "'");
		String ss = "\t" + s + "\n";
		methodBody.append(ss);
	}
}
