package org.collage.example;

import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.IXCommand;

import java.util.HashMap;
import java.util.Map;

public class X001
{
	public static void main(String[] args)
	{
		Map dataCtx = new HashMap();
		dataCtx.put("firstname", "Uli");

		IXCommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("Hi ${firstname}. How are you?");
		cmd.execute(dataCtx);
		String s = StringHandlerCV.getString(dataCtx);
		System.out.println(s);
	}
}
