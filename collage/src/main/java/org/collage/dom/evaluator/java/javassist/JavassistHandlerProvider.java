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
import org.xcommand.pattern.observer.BasicNotifier;

public class JavassistHandlerProvider {
    public ICommand newEnterRootObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new EnterRootHandler());
        return result;
    }

    public ICommand newExitRootObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new ExitRootHandler());
        return result;
    }

    public ICommand newTextObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToTextTransformer());
        result.registerObserver(new TextToStringExtractor());
        result.registerObserver(new JavaEvalTextHandler());
        return result;
    }

    public ICommand newVariableObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToVariableTransformer());
        result.registerObserver(new VariableToVariableNameExtractor());
        result.registerObserver(new JavaEvalVariableHandler());
        return result;
    }

    public ICommand newJavaObserver() {
        var result = new BasicNotifier();
        result.registerObserver(new DomObjToJavaTransformer());
        result.registerObserver(new JavaEvalJavaHandler());
        return result;
    }
}
