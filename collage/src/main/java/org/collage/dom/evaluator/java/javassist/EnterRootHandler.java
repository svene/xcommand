package org.collage.dom.evaluator.java.javassist;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

class EnterRootHandler implements ICommand {
	@Override
	public void execute() {
		StringBuffer methodBody = new StringBuffer(1024);
		TCP.getContext().put("methodbody", methodBody);
	}
}
