package org.collage.dom.evaluator.text;

import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.BasicNotifier;

public class TextHandlerProvider {
    public ICommand newTextObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToTextTransformer());
        result.registerObserver(new TextToStringExtractor());
        return result;
    }

    public ICommand newVariableObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToVariableTransformer());
        result.registerObserver(new VariableToVariableNameExtractor());
        result.registerObserver(new VariableNameToValueTransformer());
        return result;
    }

    public ICommand newJavaObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToJavaTransformer());
        result.registerObserver(new JavaToStringExtractor());
        return result;
    }
}
