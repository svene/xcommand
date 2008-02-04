package org.xcommand.template.jst;

import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.template.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class DefaultJSTParserProvider implements IJSTParserProvider
{
	public JSTParser newJSTParser(Map aCtx)
	{
		String encoding = JSTParserCV.getEncoding(aCtx);
		if (encoding == null) encoding = "UTF-8";
		JSTParser parser = new JSTParser(JSTParserCV.getInputStream(aCtx), encoding);
		parser.getJavaVarNotifier().registerObserver(javaVariableObserver);
		parser.getJavaTextNotifier().registerObserver(javaTextObserver);
		parser.getCommentStartNotifier().registerObserver(commentStartObserver);
		parser.getCommentTextNotifier().registerObserver(commentTextObserver);
		parser.getCommentEndNotifier().registerObserver(commentEndObserver);
		parser.getEolInCommentNotifier().registerObserver(eolInCommentObserver);
		parser.getEolInJavaNotifier().registerObserver(eolInJavaObserver);
		return parser;
	}

	private IXCommand javaVariableObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("javavariable found: " + ParserCV.getValue(aCtx));
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append("\");");
				code.append("$s(");
				code.append(ParserCV.getValue(aCtx));
				code.append(");");
				code.append("$s(\"");
			}
		};
	private IXCommand javaTextObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("javaText found: " + ParserCV.getValue(aCtx));
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append(ParserCV.getValue(aCtx));
			}
		};
	private IXCommand commentStartObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("commentStart found");
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append("$s(\"");
			}
		};
	private IXCommand commentEndObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("commentEnd found");
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append("\");");
			}
		};
	private IXCommand commentTextObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("commentText found: " + ParserCV.getValue(aCtx));
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				String v = ParserCV.getValue(aCtx);
				if ("\"".equals(v))
				{
					v = "\\\"";
				}
				code.append(v);
			}
		};
	private IXCommand eolObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("eol found");
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append('\n');
			}
		};
	private IXCommand eolInCommentObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("comment eol found");
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append("\\n");
			}
		};
	private IXCommand eolInJavaObserver = new IXCommand()
		{
			public void execute(Map aCtx)
			{
//				System.out.println("java eol found");
				StringBuffer code = JSTParserCV.getGeneratedJavaCode(aCtx);
				code.append('\n');
			}
		};

}
