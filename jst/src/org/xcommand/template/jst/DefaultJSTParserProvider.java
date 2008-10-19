package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.template.parser.IParserCV;

public class DefaultJSTParserProvider implements IJSTParserProvider
{
	public JSTParser newJSTParser()
	{
		String encoding = jstParserCV.getEncoding();
		if (encoding == null) encoding = "UTF-8";
		JSTParser parser = new JSTParser(jstParserCV.getInputStream(), encoding);
		parser.getJavaVarNotifier().registerObserver(javaVariableObserver);
		parser.getJavaTextNotifier().registerObserver(javaTextObserver);
		parser.getCommentStartNotifier().registerObserver(commentStartObserver);
		parser.getCommentTextNotifier().registerObserver(commentTextObserver);
		parser.getCommentEndNotifier().registerObserver(commentEndObserver);
		parser.getEolInCommentNotifier().registerObserver(eolInCommentObserver);
		parser.getEolInJavaNotifier().registerObserver(eolInJavaObserver);
		return parser;
	}

	private ICommand javaVariableObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("javavariable found: " + ParserCV.getValue(aCtx));
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append("\");");
				code.append("$s(");
				code.append(parserCV.getValue());
				code.append(");");
				code.append("$s(\"");
			}
		};
	private ICommand javaTextObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("javaText found: " + ParserCV.getValue(aCtx));
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append(parserCV.getValue());
			}
		};
	private ICommand commentStartObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("commentStart found");
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append("$s(\"");
			}
		};
	private ICommand commentEndObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("commentEnd found");
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append("\");");
			}
		};
	private ICommand commentTextObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("commentText found: " + ParserCV.getValue(aCtx));
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				String v = parserCV.getValue();
				if ("\"".equals(v))
				{
					v = "\\\"";
				}
				code.append(v);
			}
		};
	private ICommand eolObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("eol found");
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append('\n');
			}
		};
	private ICommand eolInCommentObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("comment eol found");
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append("\\n");
			}
		};
	private ICommand eolInJavaObserver = new ICommand()
		{
			public void execute()
			{
//				System.out.println("java eol found");
				StringBuffer code = jstParserCV.getGeneratedJavaCode();
				code.append('\n');
			}
		};

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	private IJSTParserCV jstParserCV = (IJSTParserCV) dbp.newBeanForInterface(IJSTParserCV.class);
}
