package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.TextCV;
import org.collage.dom.ast.Text;
import org.collage.dom.evaluator.EvaluationCV;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

/**
 * Read value of Text via TextCV.getText() and put it on aCtx via `StringHandlerCV.setString'
 */
public class TextToStringExtractor implements IXCommand
{
	public void execute(Map aCtx)
	{
		if (StringHandlerCV.getString(aCtx) == null && EvaluationCV.getWriter(aCtx) == null)
			throw new IllegalStateException("StringHandlerCV.getString(aCtx) == null && EvaluationCV.getWriter(aCtx) == null");
		Text text = TextCV.getText(aCtx);
		if (StringHandlerCV.getString(aCtx) != null)
		{
			String s = text.getValue();
			StringHandlerCV.setString(aCtx, s);
		}
		Writer writer = EvaluationCV.getWriter(aCtx);
		if (writer != null)
		{
			try
			{
				String s = text.getValue();
//				writer.write(text.getStream()); // not existing yet!
				writer.write(s);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}

		}
	}
}
