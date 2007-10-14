package org.collage.dom.evaluator.text;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.JavaCV;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.xcommand.core.IXCommand;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JavaToStringExtractor implements IXCommand
{
	public void execute(Map aCtx)
	{
		Java java = JavaCV.getJava(aCtx);
		String s = "<?java" + java.getValue() + "?>";
		if (StringHandlerCV.getString(aCtx) != null)
		{
			StringHandlerCV.setString(aCtx, s);
		}
		Writer writer = EvaluationCV.getWriter(aCtx);
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

}
