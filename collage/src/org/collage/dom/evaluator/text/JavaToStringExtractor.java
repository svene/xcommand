package org.collage.dom.evaluator.text;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.IJavaCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.IEvaluationCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

import java.io.IOException;
import java.io.Writer;

public class JavaToStringExtractor implements ICommand
{
	public void execute()
	{
		Java java = javaCV.getJava();
		String s = "<?java" + java.getValue() + "?>";
		if (stringHandlerCV.getString() != null)
		{
			stringHandlerCV.setString(s);
		}
		Writer writer = evaluationCV.getWriter();
		if (writer != null)
		{
			try
			{
				writer.write("<?java");
//			writer.write(java.getStream()); // not existing yet!
				writer.write(java.getValue());
				writer.write("?>");

			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private DynaBeanProvider dbp = new DynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
	IJavaCV javaCV = (IJavaCV) dbp.getBeanForInterface(IJavaCV.class);
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.getBeanForInterface(IEvaluationCV.class);
}
