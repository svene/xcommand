package org.collage.dom.evaluator.text;

import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.SubjectImpl;

public class TextHandlerProvider
{
	public IXCommand newTextObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToTextTransformer());
		result.registerObserver(new TextToStringExtractor());
		return result;
	}
	public IXCommand newVariableObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToVariableTransformer());
		result.registerObserver(new VariableToVariableNameExtractor());
		result.registerObserver(new VariableNameToValueTransformer());
		return result;
	}
	public IXCommand newJavaObserver()
	{
		SubjectImpl result = new SubjectImpl();
		result.registerObserver(new DomObjToJavaTransformer());
		result.registerObserver(new JavaToStringExtractor());
		return result;
	}
}
