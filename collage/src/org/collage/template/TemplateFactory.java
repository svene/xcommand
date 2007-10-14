package org.collage.template;

import org.collage.dom.evaluator.common.StringHandlerCV;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class TemplateFactory
{
	public static IXCommand newRecursiveTemplateInstance(TemplateSource aTemplateSource) throws Exception
	{
		String sOld = "";

		IXCommand cmd = new TextTemplateCompiler().newTemplateCommand(aTemplateSource);
		Map cctx = aTemplateSource.getContext();
		cmd.execute(cctx);
		String s = StringHandlerCV.getString(aTemplateSource.getContext());

		while (!sOld.equals(s))
		{
			sOld = s;

			cmd = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(s, cctx));
			cmd.execute(cctx);
			s = StringHandlerCV.getString(cctx);
		}
		cmd = new JavassistTemplateCompiler().newTemplateCommand(new TemplateSource(s, cctx));

		return cmd;
	}
}
