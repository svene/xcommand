package org.xcommand.template.jst.parser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Stream;
import org.jooq.lambda.Sneaky;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

class JSTParserTest {

    void initializeContext() {
        TCP.pushContext(new HashMap<>());
    }

    void tearDownContext() {
        TCP.popContext();
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> testCommentStartHandler() {
        return Stream.of(
                Arguments.of("hi there!", 0),
                Arguments.of("hi /*#there!", 1),
                Arguments.of("""
				hi /*#there!
				sdf#*/
				""", 1));
    }

    @ParameterizedTest
    @MethodSource
    void testCommentStartHandler(String input, int expected) throws ParseException {
        TCP.start(Sneaky.runnable(() -> {
            initializeContext();
            var parser = new JSTParser(inputStreamFromString(input));
            var commentStartHandler = Mockito.mock(ICommand.class);
            parser.getCommentStartNotifier().registerObserver(commentStartHandler);
            parser.Start();
            verify(commentStartHandler, times(expected)).execute();
            tearDownContext();
        }));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> testEolInCommentHandler() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileInputStream("../jst-testdata/src/main/java/T1.java"), 3),
                Arguments.of(inputStreamFromString("""
				hi /*#there!
				blabla
				sdf#*/
				"""), 2));
    }

    @ParameterizedTest
    @MethodSource
    void testEolInCommentHandler(InputStream is, int expected) {
        TCP.start(Sneaky.runnable(() -> {
            var parser = new JSTParser(is);
            var handler = Mockito.mock(ICommand.class);
            parser.getEolInCommentNotifier().registerObserver(handler);
            parser.Start();
            verify(handler, times(expected)).execute();
        }));
    }

    // TODO: add tests for other callbacks

    private static InputStream inputStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
}
