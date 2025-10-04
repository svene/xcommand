package org.collage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.CachingTextTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

class CachingTextTemplateCompilerTest {

    private static final int RUNS = 10000;

    @Test
    void exerciseTextTemplateCompiler() {
        TCP.start(() -> {
            TCP.getContext().put("firstname", "Uli");
            IntStream.range(0, RUNS).forEach(it -> {
                new TextTemplateCompiler()
                        .newTemplateCommandFromString("hallo ${firstname}. Wie gehts?")
                        .execute();
                assertEquals("hallo Uli. Wie gehts?", stringHandlerCV.getString());
            });
        });
    }

    /**
     * Much faster than previous routine
     */
    @Test
    void exerciseCachingTextTemplateCompiler() {
        TCP.start(() -> {
            TCP.getContext().put("firstname", "Uli");
            // On first template request `CachingTextTemplateCompiler' will compile unknown template:
            new CachingTextTemplateCompiler()
                    .getTemplateCommand("hallo ${firstname}. Wie gehts?")
                    .execute();
            assertEquals("hallo Uli. Wie gehts?", stringHandlerCV.getString());

            // For further template request `CachingTextTemplateCompiler' should find template in cache:
            IntStream.range(0, RUNS).forEach(it -> {
                new CachingTextTemplateCompiler()
                        .getTemplateCommand("hallo ${firstname}. Wie gehts?")
                        .execute();
                assertEquals("hallo Uli. Wie gehts?", stringHandlerCV.getString());
            });
        });
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
