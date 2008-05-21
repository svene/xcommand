package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;

public class JavaEvalJavaHandler implements ICommand
{
	public void execute()
	{
		Java java = javaCV.getJava();
		String s = java.getValue();

		StringBuffer methodBody = (StringBuffer) TCP.getContext().get("methodbody");
//		System.out.println("*** javacode: '" + s + "'");
		String ss = "\t" + s + "\n";
		methodBody.append(ss);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IJavaCV javaCV = (IJavaCV) dbp.getBeanForInterface(IJavaCV.class);
}
