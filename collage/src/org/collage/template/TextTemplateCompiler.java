package org.collage.template;

import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TextTemplateCompiler
{

	public TemplateCommand newTemplateCommandFromString(String aString)
	{
		return newTemplateCommand(new TemplateSource(aString));
	}

	public TemplateCommand newTemplateCommand(TemplateSource aTemplateSource)
	{
		// Compile template:
		Map ctx = new HashMap();
		ctx.putAll(TemplateCompiler.getConfigCtx());
		ctx.putAll(aTemplateSource.getContext());
		ParserCV.setInputStream(ctx, aTemplateSource.getInputStream());
		new TemplateCompiler().execute(ctx);
		ITreeNode rootNode = TreeNodeCV.getTreeNode(ctx);
		return new TextTemplateEvaluationCommand(rootNode);
	}

// --- Implementation ---

	private class TextTemplateEvaluationCommand extends TemplateCommand
	{

		public TextTemplateEvaluationCommand(ITreeNode aRootNode)
		{
			super(aRootNode);
			setNotifyingTreeNodeTraverser(new TextTraverser());
		}
	}
}
