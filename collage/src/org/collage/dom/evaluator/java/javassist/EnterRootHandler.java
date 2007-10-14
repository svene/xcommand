package org.collage.dom.evaluator.java.javassist;

import org.xcommand.core.IXCommand;

import java.util.Map;

class EnterRootHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer methodBody = new StringBuffer(1024);
		aCtx.put("methodbody", methodBody);
	}
}
