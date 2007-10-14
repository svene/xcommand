package org.collage;

import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Map;

public class DomDumpingTestHelper
{

	public IXCommand getTextDumper()
	{
		return textDumper;
	}

	public IXCommand getVariableDumper()
	{
		return variableDumper;
	}

	public IXCommand getJavaDumper()
	{
		return javaDumper;
	}

	private IXCommand textDumper = new IXCommand()
	{

		public void execute(Map aCtx)
		{
			String s = StringHandlerCV.getString(aCtx);
			s = "@@@ TEXT: '" + s + "'";
			StringHandlerCV.setString(aCtx, s);
		}
	};
	private IXCommand variableDumper = new IXCommand()
	{

		public void execute(Map aCtx)
		{
			String s = StringHandlerCV.getString(aCtx);
			s = "@@@ VARIABLE: '" + s + "'";
			StringHandlerCV.setString(aCtx, s);
		}
	};
	private IXCommand javaDumper = new IXCommand()
	{

		public void execute(Map aCtx)
		{
			String s = StringHandlerCV.getString(aCtx);
			s = "@@@ JAVA CODE: '" + s + "'";
			StringHandlerCV.setString(aCtx, s);
		}
	};

// --- Pre constructed observers ---

	public IXCommand newDomDumpingTextObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToTextTransformer());
		result.registerObserver(new TextToStringExtractor());
		result.registerObserver(getTextDumper());
		return result;
	}
	public IXCommand newDomDumpingVariableObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToVariableTransformer());
		result.registerObserver(new VariableToVariableNameExtractor());
		result.registerObserver(getVariableDumper());
		return result;
	}
	public IXCommand newDomDumpingJavaObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToJavaTransformer());
		result.registerObserver(new JavaToStringExtractor());
		result.registerObserver(getJavaDumper());
		return result;
	}

	TestHelper th = new TestHelper();
}