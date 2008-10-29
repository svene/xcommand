package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.*;

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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IJavaCV javaCV = (IJavaCV) dbp.newBeanForInterface(IJavaCV.class);
}
