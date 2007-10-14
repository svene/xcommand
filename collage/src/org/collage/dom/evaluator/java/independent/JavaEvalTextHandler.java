package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class JavaEvalTextHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer methodBody = (StringBuffer) aCtx.get("methodbody");
		String s = StringHandlerCV.getString(aCtx);
		String ss = decodedString("\t_writer.write(#") + s + decodedString("#);\n");
		methodBody.append(ss);
	}

	private String decodedString(String aString)
	{
		return StringUtils.replace(aString, "#", "\"");
	}
}
