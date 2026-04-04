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

    private final ICommand javaVariableObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("javavariable found: " + parserCV.getValue());
            var code = jstParserCV.getGeneratedJavaCode();
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
            log.debug("javaText found: " + parserCV.getValue());
            var code = jstParserCV.getGeneratedJavaCode();
            code.append(parserCV.getValue());
        }
    };
    private final ICommand commentStartObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("commentStart found");
            var code = jstParserCV.getGeneratedJavaCode();
            code.append("$s(\"");
        }
    };
    private final ICommand commentEndObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("commentEnd found");
            var code = jstParserCV.getGeneratedJavaCode();
            code.append("\");");
        }
    };
    private final ICommand commentTextObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("commentText found: " + parserCV.getValue());
            var code = jstParserCV.getGeneratedJavaCode();
            var v = parserCV.getValue();
            if ("\"".equals(v)) {
                v = "\\\"";
            }
            code.append(v);
        }
    };

    @SuppressWarnings("unused")
    private final ICommand eolObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("eol found");
            var code = jstParserCV.getGeneratedJavaCode();
            code.append('\n');
        }
    };

    private final ICommand eolInCommentObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("comment eol found");
            var code = jstParserCV.getGeneratedJavaCode();
            code.append("\\n");
        }
    };
    private final ICommand eolInJavaObserver = new ICommand() {
        @Override
        public void execute() {
            log.debug("java eol found");
            var code = jstParserCV.getGeneratedJavaCode();
            code.append('\n');
        }
    };

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
