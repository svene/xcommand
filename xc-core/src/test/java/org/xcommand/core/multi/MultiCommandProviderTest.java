package org.xcommand.core.multi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MultiCommandProviderTest {

    // ---------------------------------------------------------------------------
    // Shared fixture: two valid Map-accepting commands + two that must be ignored
    // ---------------------------------------------------------------------------

    /** Used by fromClass — must have a public no-arg constructor. */
    public static class SampleCommands {
        String lastCalled;

        public void hello(Map<String, Object> ctx) {
            lastCalled = "hello";
        }

        public void bye(Map<String, Object> ctx) {
            lastCalled = "bye";
        }

        /** No parameter — must NOT appear in commandMap. */
        public void noParam() {}

        /** Wrong parameter type — must NOT appear in commandMap. */
        public void wrongParam(String s) {}
    }

    static Stream<MultiCommandProvider> providers() {
        return Stream.of(
                MultiCommandProvider.fromClass(SampleCommands.class),
                MultiCommandProvider.fromObject(new SampleCommands()));
    }

    // ---------------------------------------------------------------------------
    // Parameterized tests — identical behaviour for both factories
    // ---------------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("providers")
    void commandMap_contains_exactly_the_map_accepting_methods(MultiCommandProvider provider) {
        assertThat(provider.getCommandMap()).containsOnlyKeys("hello", "bye");
    }

    @ParameterizedTest
    @MethodSource("providers")
    void getCommand_returns_non_null_for_known_names(MultiCommandProvider provider) {
        assertThat(provider.getCommand("hello")).isNotNull();
        assertThat(provider.getCommand("bye")).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("providers")
    void getCommand_returns_null_for_unknown_name(MultiCommandProvider provider) {
        assertThat(provider.getCommand("noParam")).isNull();
        assertThat(provider.getCommand("wrongParam")).isNull();
        assertThat(provider.getCommand("nonExistent")).isNull();
    }

    @ParameterizedTest
    @MethodSource("providers")
    void executing_a_command_does_not_throw(MultiCommandProvider provider) {
        provider.getCommand("hello").execute();
        provider.getCommand("bye").execute();
    }

    // ---------------------------------------------------------------------------
    // fromObject-specific: verify commands are bound to the original instance
    // ---------------------------------------------------------------------------

    @Test
    void executing_hello_command_invokes_hello_on_the_original_object() {
        var target = new SampleCommands();
        var provider = MultiCommandProvider.fromObject(target);

        provider.getCommand("hello").execute();

        assertThat(target.lastCalled).isEqualTo("hello");
    }

    @Test
    void executing_bye_command_invokes_bye_on_the_original_object() {
        var target = new SampleCommands();
        var provider = MultiCommandProvider.fromObject(target);

        provider.getCommand("bye").execute();

        assertThat(target.lastCalled).isEqualTo("bye");
    }
}
