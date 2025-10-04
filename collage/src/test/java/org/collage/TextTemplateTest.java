package org.collage;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.HashMap;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

class TextTemplateTest {
    static String FIRSTNAME = "Denis";
    static String LASTNAME = "Bogan";

    StringWriter stringWriter;

    void initializeContext() {
        TCP.pushContext(new HashMap<>());
        TCP.getContext().put("firstname", FIRSTNAME);

        stringWriter = new StringWriter();
    }

    void tearDownContext() {
        TCP.popContext();
    }

    private String createTemplate(String in) {
        new TextTemplateCompiler().newTemplateCommandFromString(in).execute();
        return stringHandlerCV.getString();
    }

    @Nested
    class Basic {
        @Test
        void test1() {
            TCP.start(() -> {
                initializeContext();
                String out = createTemplate("hallo ${firstname}.\nWie gehts?\n");
                assertThat(out).isEqualTo("hallo %s.%nWie gehts?%n".formatted(FIRSTNAME));
                tearDownContext();
            });
        }

        @Test
        void test2() {
            TCP.start(() -> {
                initializeContext();
                var tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
                tc.setWriter(stringWriter);
                tc.execute();
                assertThat(stringWriter.toString()).isEqualTo("hallo %s.%nWie gehts?%n".formatted(FIRSTNAME));
                tearDownContext();
            });
        }

        @Test
        void verfiyProperFunctionIfInputComesFromFile() {
            TCP.start(() -> {
                initializeContext();
                var tc = new TextTemplateCompiler().newTemplateCommandFromResourceName("in.txt");
                tc.setWriter(stringWriter);
                tc.execute();
                assertThat(stringWriter.toString())
                        .isEqualTo("Hallo %s! Willkommen bei uns.%n<?java int i = 1 ?>d%n".formatted(FIRSTNAME));
                tearDownContext();
            });
        }

        @Test
        void test5() {
            TCP.start(() -> {
                initializeContext();
                domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
                var tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");

                TCP.getContext().put("firstname", FIRSTNAME);
                tc.setWriter(stringWriter);
                tc.execute();
                assertThat(stringWriter.toString()).isEqualTo("hallo %s.%nWie gehts?%n".formatted(FIRSTNAME));
                tearDownContext();
            });
        }
    }

    /**
     * Demonstrate recursive template handling
     */
    @Nested
    class Recursive {
        @Test
        void recursive_placeholder_handling() {
            TCP.start(() -> {
                initializeContext();
                TCP.getContext().put("name", "${firstname} ${lastname}");
                assertThat(createTemplate("hallo ${name}. Wie gehts?"))
                        .isEqualTo("hallo ${firstname} ${lastname}. Wie gehts?");
                tearDownContext();
            });
        }
    }

    @Nested
    class Misc {
        @Test
        void unknowns_are_not_replaced() {
            TCP.start(() -> {
                initializeContext();
                assertThat(createTemplate("hallo ${lastname}. Wie gehts?")).isEqualTo("hallo ${lastname}. Wie gehts?");
                tearDownContext();
            });
        }

        @Test
        void only_knowns_are_replaced() {
            TCP.start(() -> {
                initializeContext();
                assertThat(createTemplate("hallo ${firstname} ${lastname}. Wie gehts?"))
                        .isEqualTo("hallo %s ${lastname}. Wie gehts?".formatted(FIRSTNAME));
                tearDownContext();
            });
        }

        @Test
        void javassist_template() {
            TCP.start(() -> {
                initializeContext();
                var cmd = new JavassistTemplateCompiler(new JavassistTraverser())
                        .newTemplateCommandFromString("hallo ${lastname}. Wie gehts?");
                TCP.getContext().put("firstname", FIRSTNAME);
                TCP.getContext().put("lastname", LASTNAME);
                TemplateCV.setWriter(stringWriter);
                cmd.execute();
                assertThat(stringWriter.toString()).isEqualTo("hallo %s. Wie gehts?".formatted(LASTNAME));
                tearDownContext();
            });
        }
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    final IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}
