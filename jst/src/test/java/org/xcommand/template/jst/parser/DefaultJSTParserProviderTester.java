package org.xcommand.template.jst.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.jooq.lambda.Sneaky;
import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.template.jst.DefaultJSTParserProvider;
import org.xcommand.template.jst.IJSTParserCV;

class DefaultJSTParserProviderTester {

    private void initializeContext() {
        TCP.pushContext(new HashMap<>());
        jstParserCV.setGeneratedJavaCode(new StringBuffer());
    }

    private void tearDownContext() {
        TCP.popContext();
    }

    @Test
    void test1() {
        TCP.start(Sneaky.runnable(() -> {
            initializeContext();
            var parser = newJSTParser(inputStreamFromString("hi there!"));
            parser.Start();
            assertEquals("hi there!", jstParserCV.getGeneratedJavaCode().toString());
            tearDownContext();
        }));
    }

    @Test
    void test2() {
        TCP.start(Sneaky.runnable(() -> {
            initializeContext();
            var parser = newJSTParser(inputStreamFromString("hi there! /*#some comment#*/"));
            parser.Start();
            assertEquals(
                    "hi there! $s(\"some comment\");",
                    jstParserCV.getGeneratedJavaCode().toString());
            tearDownContext();
        }));
    }

    @Test
    void test3() {
        TCP.start(Sneaky.runnable(() -> {
            initializeContext();
            var parser = newJSTParser(inputStreamFromString("hi there! /*#af $jv{somename} jj#*/"));
            parser.Start();
            assertEquals(
                    "hi there! $s(\"af \");$s(somename);$s(\" jj\");",
                    jstParserCV.getGeneratedJavaCode().toString());
            tearDownContext();
        }));
    }

    @Test
    void testMultiline() {
        var input = """
			hi there! /*#af $jv{somename} jj#*/
			how are you?
			""";
        TCP.start(Sneaky.runnable(() -> {
            initializeContext();
            var parser = newJSTParser(inputStreamFromString(input));
            parser.Start();

            assertEquals(
                    "hi there! $s(\"af \");$s(somename);$s(\" jj\");\nhow are you?\n",
                    jstParserCV.getGeneratedJavaCode().toString());
            tearDownContext();
        }));
    }

    private InputStream inputStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }

    private JSTParser newJSTParser(InputStream aIs) {
        return TCP.get(() -> {
            jstParserCV.setInputStream(aIs);
            return new DefaultJSTParserProvider().newJSTParser();
        });
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
