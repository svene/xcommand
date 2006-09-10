package org.collage.dom.evaluator.text;

import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;

import java.io.Writer;
import java.util.Map;

public class RootNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		String mode = (String) aCtx.get("mode");
		if ("exit".equals(mode))
		{
			Writer w = EvaluationContextView.getWriter(aCtx);
			try
			{
				w.flush();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

}
