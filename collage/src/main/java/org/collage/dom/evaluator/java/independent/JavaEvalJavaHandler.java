package org.collage.dom.evaluator.java.independent;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.*;

public class JavaEvalJavaHandler implements ICommand {
	@Override
	public void execute() {
		var java = javaCV.getJava();
		var s = java.getValue();

		var methodBody = (StringBuffer) TCP.getContext().get("methodbody");
//		System.out.println("*** javacode: '" + s + "'");
		var ss = "\t" + s + "\n";
		methodBody.append(ss);
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}
