package org.collage.dom.evaluator.java.independent;

import org.apache.commons.lang.StringUtils;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.collage.dom.evaluator.common.IStringHandlerCV;

public class JavaEvalTextHandler implements ICommand
{
	public void execute()
	{
		// TODO: improve this:
		StringBuffer methodBody = (StringBuffer) TCP.getContext().get("methodbody");
		String s = stringHandlerCV.getString();
		String ss = decodedString("\t_writer.write(#") + s + decodedString("#);\n");
		methodBody.append(ss);
	}

	private String decodedString(String aString)
	{
		return StringUtils.replace(aString, "#", "\"");
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
