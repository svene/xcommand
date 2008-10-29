package org.collage.template;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

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
	private static final IDynaBeanProvider dbp =
		DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private static IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
