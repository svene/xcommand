package org.collage.template;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

public class TemplateFactory
{
	public static ICommand newRecursiveTemplateInstance(TemplateSource aTemplateSource) throws Exception
	{
		String sOld = "";

		ICommand cmd = new TextTemplateCompiler().newTemplateCommand(aTemplateSource);
//!!		Map cctx = aTemplateSource.getContext();
		cmd.execute();
		String s = stringHandlerCV.getString();
//		System.out.println("-----------------------");
//		System.out.println(s);

		while (!sOld.equals(s))
		{
			sOld = s;

			cmd = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(s));
			cmd.execute();
			s = stringHandlerCV.getString();
//			System.out.println("-----------------------");
//			System.out.println(s);
		}
		cmd = new JavassistTemplateCompiler().newTemplateCommand(new TemplateSource(s));

		return cmd;
	}
	private static DynaBeanProvider dbp = new DynaBeanProvider();
	private static IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
}
