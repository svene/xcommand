package org.collage;

import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.pattern.observer.BasicNotifier;

public class DomDumpingTestHelper
{

	public ICommand getTextDumper()
	{
		return textDumper;
	}

	public ICommand getVariableDumper()
	{
		return variableDumper;
	}

	public ICommand getJavaDumper()
	{
		return javaDumper;
	}

	private ICommand textDumper = new ICommand()
	{

		public void execute()
		{
			String s = stringHandlerCV.getString();
			s = "@@@ TEXT: '" + s + "'";
			stringHandlerCV.setString(s);
		}
	};
	private ICommand variableDumper = new ICommand()
	{

		public void execute()
		{
			String s = stringHandlerCV.getString();
			s = "@@@ VARIABLE: '" + s + "'";
			stringHandlerCV.setString(s);
		}
	};
	private ICommand javaDumper = new ICommand()
	{

		public void execute()
		{
			String s = stringHandlerCV.getString();
			s = "@@@ JAVA CODE: '" + s + "'";
			stringHandlerCV.setString(s);
		}
	};

// --- Pre constructed observers ---

	public ICommand newDomDumpingTextObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToTextTransformer());
		result.registerObserver(new TextToStringExtractor());
		result.registerObserver(getTextDumper());
		return result;
	}
	public ICommand newDomDumpingVariableObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToVariableTransformer());
		result.registerObserver(new VariableToVariableNameExtractor());
		result.registerObserver(getVariableDumper());
		return result;
	}
	public ICommand newDomDumpingJavaObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToJavaTransformer());
		result.registerObserver(new JavaToStringExtractor());
		result.registerObserver(getJavaDumper());
		return result;
	}

	TestHelper th = new TestHelper();
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}