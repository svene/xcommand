package org.collage.dom.evaluator.java.javassist;

import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.java.independent.JavaEvalJavaHandler;
import org.collage.dom.evaluator.java.independent.JavaEvalTextHandler;
import org.collage.dom.evaluator.java.independent.JavaEvalVariableHandler;
import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.pattern.observer.BasicNotifier;

public class JavassistHandlerProvider
{
	public ICommand newEnterRootObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new EnterRootHandler());
		return result;
	}
	public ICommand newExitRootObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new ExitRootHandler());
		return result;
	}

	public ICommand newTextObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToTextTransformer());
		result.registerObserver(new TextToStringExtractor());
		result.registerObserver(new JavaEvalTextHandler());
		return result;
	}

	public ICommand newVariableObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToVariableTransformer());
		result.registerObserver(new VariableToVariableNameExtractor());
		result.registerObserver(new JavaEvalVariableHandler());
		return result;
	}

	public ICommand newJavaObserver()
	{
		AbstractBasicNotifier result = new BasicNotifier();
		result.registerObserver(new DomObjToJavaTransformer());
		result.registerObserver(new JavaEvalJavaHandler());
		return result;
	}


}
