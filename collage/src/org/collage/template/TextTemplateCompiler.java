package org.collage.template;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.parser.ParserCV;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

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
		new DefaultDomNodeCreationHandlerInitializer().execute(ctx);

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
