package org.collage.dom.evaluator.java.independent;

import org.apache.commons.lang.StringUtils;
import org.xcommand.core.*;
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
