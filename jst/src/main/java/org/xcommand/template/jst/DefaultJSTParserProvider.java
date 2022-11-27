package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.template.parser.IParserCV;

import java.nio.charset.StandardCharsets;

public class DefaultJSTParserProvider implements IJSTParserProvider {
	@Override
	public JSTParser newJSTParser() {
		String encoding = jstParserCV.getEncoding();
		if (encoding == null) {
			encoding = StandardCharsets.UTF_8.name();
		}
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

	private final ICommand javaVariableObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("javavariable found: " + ParserCV.getValue(aCtx));
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append("\");");
			code.append("$s(");
			code.append(parserCV.getValue());
			code.append(");");
			code.append("$s(\"");
		}
	};
	private final ICommand javaTextObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("javaText found: " + ParserCV.getValue(aCtx));
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append(parserCV.getValue());
		}
	};
	private final ICommand commentStartObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("commentStart found");
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append("$s(\"");
		}
	};
	private final ICommand commentEndObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("commentEnd found");
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append("\");");
		}
	};
	private final ICommand commentTextObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("commentText found: " + ParserCV.getValue(aCtx));
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			String v = parserCV.getValue();
			if ("\"".equals(v)) {
				v = "\\\"";
			}
			code.append(v);
		}
	};
	private final ICommand eolObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("eol found");
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append('\n');
		}
	};
	private final ICommand eolInCommentObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("comment eol found");
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append("\\n");
		}
	};
	private final ICommand eolInJavaObserver = new ICommand() {
		@Override
		public void execute() {
//				System.out.println("java eol found");
			StringBuffer code = jstParserCV.getGeneratedJavaCode();
			code.append('\n');
		}
	};

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
