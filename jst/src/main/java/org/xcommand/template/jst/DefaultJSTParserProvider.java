package org.xcommand.template.jst;

import lombok.extern.slf4j.Slf4j;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class DefaultJSTParserProvider implements IJSTParserProvider {
    @Override
    public JSTParserWrapper newJSTParser() {
        var pw = new JSTParserWrapper();
        pw.getNotifiers().javaVarNotifier.registerObserver(javaVariableObserver);
        pw.getNotifiers().javaTextNotifier.registerObserver(javaTextObserver);
        pw.getNotifiers().commentStartNotifier.registerObserver(commentStartObserver);
        pw.getNotifiers().commentTextNotifier.registerObserver(commentTextObserver);
        pw.getNotifiers().commentEndNotifier.registerObserver(commentEndObserver);
        pw.getNotifiers().eolInCommentNotifier.registerObserver(eolInCommentObserver);
        pw.getNotifiers().eolInJavaNotifier.registerObserver(eolInJavaObserver);
        return pw;
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);

    private void appendCode(String... parts) {
        var code = jstParserCV.getGeneratedJavaCode();
        for (String part : parts) {
            code.append(part);
        }
    }

    private final ICommand javaVariableObserver = () -> {
        log.debug("javavariable found: " + parserCV.getValue());
        appendCode("\");", "$s(", parserCV.getValue(), ");", "$s(\"");
    };
    private final ICommand javaTextObserver = () -> {
        log.debug("javaText found: " + parserCV.getValue());
        appendCode(parserCV.getValue());
    };
    private final ICommand commentStartObserver = () -> {
        log.debug("commentStart found");
        appendCode("$s(\"");
    };
    private final ICommand commentEndObserver = () -> {
        log.debug("commentEnd found");
        appendCode("\");");
    };
    private final ICommand commentTextObserver = () -> {
        var v = parserCV.getValue();
        log.debug("commentText found: " + v);
        appendCode("\"".equals(v) ? "\\\"" : v);
    };

    @SuppressWarnings("unused")
    private final ICommand eolObserver = () -> {
        log.debug("eol found");
        appendCode("\n");
    };

    private final ICommand eolInCommentObserver = () -> {
        log.debug("comment eol found");
        appendCode("\\n");
    };
    private final ICommand eolInJavaObserver = () -> {
        log.debug("java eol found");
        appendCode("\n");
    };
}
