package org.xcommand.java;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Examples in the form of tests to demonstrate how multiline strings can be used.
 */
public class MultilineStringTest {
    @Test
    void endOnNewLine() {
        var s = """
			!one
			two
			three$
			""";
        assertThat(s.charAt(0)).isEqualTo('!');
        assertThat(s.charAt(s.length() - 1)).isEqualTo('\n');
        assertThat(s.charAt(s.length() - 2)).isEqualTo('$');
    }

    @Test
    void endOnLastLine() {
        var s = """
			!one
			two
			three$""";
        assertThat(s.charAt(0)).isEqualTo('!');
        assertThat(s.charAt(s.length() - 1)).isEqualTo('$');
    }

    @Test
    void endOnNewLineWithBackslash() {
        var s = """
			!one
			two
			three$\
			""";
        assertThat(s.charAt(0)).isEqualTo('!');
        assertThat(s.charAt(s.length() - 1)).isEqualTo('$');
    }

    @Test
    void allBackslashes() {
        var s = """
			!one\
			two\
			three$\
			""";
        assertThat(s).isEqualTo("!onetwothree$");
    }

    @Test
    void trickForOneLine() {
        var s = """
			blkj" lkkj "\
			""";
        assertThat(s).isEqualTo(cleanString("blkj“ lkkj “"));
    }

    /**
     * replace “ character in s with real doublequote.
     * Helps to avoid escaping in literal strings
     */
    private String cleanString(String s) {
        return s.replaceAll("“", "\"");
    }
}
