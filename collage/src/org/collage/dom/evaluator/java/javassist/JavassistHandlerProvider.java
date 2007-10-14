package org.collage.dom.evaluator.java.javassist;

import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.java.independent.JavaEvalTextHandler;
import org.collage.dom.evaluator.java.independent.JavaEvalVariableHandler;
import org.collage.dom.evaluator.java.independent.JavaEvalJavaHandler;
import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.SubjectImpl;

public class JavassistHandlerProvider
{
	public IXCommand newEnterRootObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new EnterRootHandler());
		return result;
	}
	public IXCommand newExitRootObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new ExitRootHandler());
		return result;
	}

	public IXCommand newTextObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToTextTransformer());
		result.registerObserver(new TextToStringExtractor());
		result.registerObserver(new JavaEvalTextHandler());
		return result;
	}

	public IXCommand newVariableObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToVariableTransformer());
		result.registerObserver(new VariableToVariableNameExtractor());
		result.registerObserver(new JavaEvalVariableHandler());
		return result;
	}

	public IXCommand newJavaObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToJavaTransformer());
		result.registerObserver(new JavaEvalJavaHandler());
		return result;
	}


}
