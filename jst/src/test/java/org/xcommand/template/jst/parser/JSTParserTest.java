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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.template.jst.IJSTParserCV;
import org.xcommand.template.jst.JSTParserWrapper;

class JSTParserTest {

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);

    void initializeContext() {
        TCP.pushContext(new HashMap<>());
    }

    void tearDownContext() {
        TCP.popContext();
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> testCommentStartHandler() {
        return Stream.of(Arguments.of("hi there!", 0), Arguments.of("hi /*#there!", 1), Arguments.of("""
				hi /*#there!
				sdf#*/
				""", 1));
    }

    @ParameterizedTest
    @MethodSource
    void testCommentStartHandler(String input, int expected) {
        TCP.start(() -> {
			initializeContext();
			var parser = new JSTParserWrapper();
			var commentStartHandler = Mockito.mock(ICommand.class);
			parser.getNotifiers().commentStartNotifier.registerObserver(commentStartHandler);
			jstParserCV.setInputStream(inputStreamFromString(input));
			parser.parse();
			verify(commentStartHandler, times(expected)).execute();
			tearDownContext();
		});
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
        TCP.start(() -> {
			var parser = new JSTParserWrapper();
			var handler = Mockito.mock(ICommand.class);
			parser.getNotifiers().eolInCommentNotifier.registerObserver(handler);
			jstParserCV.setInputStream(is);
			parser.parse();
			verify(handler, times(expected)).execute();
		});
    }

    // TODO: add tests for other callbacks

    private static InputStream inputStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
}
