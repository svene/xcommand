package org.collage.dom.evaluator.text;

import org.xcommand.core.TCP;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.IEvaluationCV;

import java.io.Writer;
import java.io.IOException;

public class VariableNameToValueTransformer implements ICommand
{
	public void execute()
	{

		String variableName = stringHandlerCV.getString();
		String result;
		Object obj = TCP.getContext().get(variableName);
		if (obj != null)
		{
			result = obj.toString();
		}
		else
		{
			result = "${" + variableName + '}';
		}
		if (stringHandlerCV.getString() != null)
		{
			stringHandlerCV.setString(result);
		}

		Writer writer = evaluationCV.getWriter();
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
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.getBeanForInterface(IEvaluationCV.class);
}
