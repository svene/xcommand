package org.collage.csm.parser;

import lombok.extern.slf4j.Slf4j;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class CsmCommands {
    public static ICommand appendJavaCodeCommand = () -> {
        var sb = CsmCommands.parserCV.getStringBuilder();
        var value = CsmCommands.parserCV.getValue();
        log.debug("appendJavaCodeCommand: value='{}'", value);
        sb.append(value);
    };

    public static ICommand appendEolCommand = () -> {
        var sb = CsmCommands.parserCV.getStringBuilder();
        Boolean javaMode = CsmCommands.domNodeCreationHandlerCV.getProduceJavaSource();
        if (javaMode) {
            sb.append('\\');
            sb.append('n');
        } else {
            sb.append("\n");
        }
        log.debug("appendEolCommand: value='{}'", sb);
    };

    public static ICommand appendTextCommand = () -> {
        var sb = CsmCommands.parserCV.getStringBuilder();
        String value = CsmCommands.parserCV.getValue();
        sb.append(value);
        log.debug("appendTextCommand: value='{}' -> '{}'", value, sb);
    };

    public static ICommand createVariableDomNodeCommand = () -> {
        var value = CsmCommands.parserCV.getValue();
        CsmCommands.domNodeCreationHandlerCV.setValue(value);
        log.debug("createVariableDomNodeCommand: value='{}'", value);
        CsmCommands.domNodeCreationHandlerCV
                .getCreateVariableNodeRequestNotifier()
                .execute();
    };

    /**
     * Commands flushing buffered text and creating associated Text-DOM-Node
     */
    public static ICommand flushJavaCommand = () -> {
        var sb = CsmCommands.parserCV.getStringBuilder();
        var s = sb.toString();
        log.debug("flushJavaCommand: value='{}'", s);
        if (s.isEmpty()) {
            return;
        }
        CsmCommands.domNodeCreationHandlerCV.setValue(s);
        CsmCommands.domNodeCreationHandlerCV.getCreateJavaNodeRequestNotifier().execute();
    };

    /**
     * Commands flushing buffered text and creating associated Text-DOM-Node
     */
    public static ICommand flushTextCommand = () -> {
        var s = CsmCommands.parserCV.getStringBuilder().toString();
        log.debug("flushTextCommand: value='{}'", s);
        // Create a Text-DOM-Node:
        CsmCommands.domNodeCreationHandlerCV.setValue(s);
        CsmCommands.domNodeCreationHandlerCV.getCreateTextNodeRequestNotifier().execute();
    };

    public static ICommand startJavaCommand = () -> {
        log.debug("startJavaCommand");
        CsmCommands.parserCV.setStringBuilder(new StringBuilder());
    };

    public static ICommand startTextCommand = () -> {
        log.debug("startTextCommand");
        CsmCommands.parserCV.setStringBuilder(new StringBuilder());
    };

    private static final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private static final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    private static final IDomNodeCreationHandlerCV domNodeCreationHandlerCV =
            dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}
