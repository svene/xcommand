package org.collage.template;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.parser.ParserCV;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;

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
		DomNodeCreationHandlerCV.setProduceJavaSource(ctx, Boolean.FALSE);
		ctx.putAll(aTemplateSource.getContext());
		new DefaultDomNodeCreationHandlerInitializer().execute(ctx);

		InputStream is = aTemplateSource.getInputStream();
		if (is == null) throw new RuntimeException("is == null");
		ParserCV.setInputStream(ctx, is);
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
