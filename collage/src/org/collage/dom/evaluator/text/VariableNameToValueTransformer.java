package org.collage.dom.evaluator.text;

import org.xcommand.core.IXCommand;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.EvaluationCV;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class VariableNameToValueTransformer implements IXCommand
{
	public void execute(Map aCtx)
	{

		String variableName = StringHandlerCV.getString(aCtx);
		String result;
		Object obj = aCtx.get(variableName);
		if (obj != null)
		{
			result = obj.toString();
		}
		else
		{
			result = "${" + variableName + '}';
		}
		if (StringHandlerCV.getString(aCtx) != null)
		{
			StringHandlerCV.setString(aCtx, result);
		}

		Writer writer = EvaluationCV.getWriter(aCtx);
		if (writer != null)
		{
			try
			{
//				writer.write(variable.getStream()); // not existing yet!
				writer.write(result);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}

		}
	}
}
