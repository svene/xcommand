package org.collage.csm.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.template.parser.IParserCV;

class CsmCommandsTest {

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);

    @Test
    void startJavaCommand_resetsStringBuilder() {
        TCP.start(() -> {
            parserCV.setStringBuilder(new StringBuilder("existing"));
            CsmCommands.startJavaCommand.execute();
            assertThat(parserCV.getStringBuilder().toString()).isEmpty();
        });
    }

    @Test
    void startTextCommand_resetsStringBuilder() {
        TCP.start(() -> {
            parserCV.setStringBuilder(new StringBuilder("existing"));
            CsmCommands.startTextCommand.execute();
            assertThat(parserCV.getStringBuilder().toString()).isEmpty();
        });
    }

    @Test
    void appendJavaCodeCommand_appendsValueToBuilder() {
        TCP.start(() -> {
            parserCV.setStringBuilder(new StringBuilder());
            parserCV.setValue("int x = 1;");
            CsmCommands.appendJavaCodeCommand.execute();
            assertThat(parserCV.getStringBuilder().toString()).isEqualTo("int x = 1;");
        });
    }

    @Test
    void appendTextCommand_appendsValueToBuilder() {
        TCP.start(() -> {
            parserCV.setStringBuilder(new StringBuilder());
            parserCV.setValue("hello");
            CsmCommands.appendTextCommand.execute();
            assertThat(parserCV.getStringBuilder().toString()).isEqualTo("hello");
        });
    }
}
