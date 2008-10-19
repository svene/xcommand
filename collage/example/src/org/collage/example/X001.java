package org.collage.example;

import org.collage.template.TextTemplateCompiler;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class X001
{
	public static void main(String[] args)
	{
		new X001().execute();
	}

	private void execute()
	{
		TCP.getContext().put("firstname", "Uli");

		ICommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("Hi ${firstname}. How are you?");
		cmd.execute();
		String s = stringHandlerCV.getString();
		System.out.println(s);
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
