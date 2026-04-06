package org.collage.template;

import org.collage.parser.TemplateParserWrapper;
import org.xcommand.core.ICommand;

public class CollageTemplateParserBuilder {
    public TemplateParserWrapper newTemplateParser(ICommand command) {
        var parser = new TemplateParserWrapper();

        //        parser.getNotifiers().startNotifier.registerObserver(
        //            () -> System.out.println("****************** STARTING PARSING *********************")
        //        );

        // connect statemachine to parser:
        parser.getNotifiers().textNotifier.registerObserver(command);
        parser.getNotifiers().javaStartNotifier.registerObserver(command);
        parser.getNotifiers().javaCodeNotifier.registerObserver(command);
        parser.getNotifiers().javaEndNotifier.registerObserver(command);
        parser.getNotifiers().varStartNotifier.registerObserver(command);
        parser.getNotifiers().varNameNotifier.registerObserver(command);
        parser.getNotifiers().varEndNotifier.registerObserver(command);
        parser.getNotifiers().eolNotifier.registerObserver(command);
        parser.getNotifiers().eofNotifier.registerObserver(command);

        return parser;
    }
}
