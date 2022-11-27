package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.*;
import org.collage.dom.evaluator.common.IStringHandlerCV;

public class JavaEvalTextHandler implements ICommand {
	@Override
	public void execute() {
		// TODO: improve this:
		StringBuffer methodBody = (StringBuffer) TCP.getContext().get("methodbody");
		String s = stringHandlerCV.getString();
		String ss = decodedString("\t_writer.write(#") + s + decodedString("#);\n");
		methodBody.append(ss);
	}

	private String decodedString(String aString) {
		return aString.replace("#", "\"");
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
