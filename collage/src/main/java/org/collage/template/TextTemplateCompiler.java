package org.collage.template;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;
import org.xcommand.util.ResourceUtil;

import java.io.InputStream;
import java.util.HashMap;

public class TextTemplateCompiler
{

	public TemplateCommand newTemplateCommandFromString(String aString)
	{
		return newTemplateCommand(new TemplateSource(aString));
	}
	public TemplateCommand newTemplateCommandFromResourceName(String aResourceName)
	{
		return newTemplateCommand(new TemplateSource(ResourceUtil.newInputStreamFromResourceLocation(aResourceName)));
	}

	public TemplateCommand newTemplateCommand(TemplateSource aTemplateSource)
	{
		// Compile template:
		TCP.pushContext(new HashMap());
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();

		InputStream is = aTemplateSource.getInputStream();
		if (is == null) throw new RuntimeException("is == null");
		parserCV.setInputStream(is);
		new TemplateCompiler().execute();
		ITreeNode rootNode = treeNodeCV.getTreeNode();
		TemplateCommand tplCmd = new TextTemplateEvaluationCommand(rootNode);
		TCP.popContext();
		return tplCmd;
	}

// --- Implementation ---

	private class TextTemplateEvaluationCommand extends TemplateCommand
	{

		private TextTemplateEvaluationCommand(ITreeNode aRootNode)
		{
			super(aRootNode);
			setNotifyingTreeNodeTraverser(new TextTraverser());
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}
