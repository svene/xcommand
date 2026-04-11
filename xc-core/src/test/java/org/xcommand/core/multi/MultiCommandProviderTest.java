package org.xcommand.core.multi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultiCommandProviderTest {

    // ---------------------------------------------------------------------------
    // Shared fixture: two valid Map-accepting commands + two that must be ignored
    // ---------------------------------------------------------------------------

    /** Used by fromClass — must have a public no-arg constructor. */
    public static class SampleCommands {
        boolean helloCalled;
        boolean byeCalled;

        public void hello(Map<String, Object> ctx) {
            helloCalled = true;
        }

        public void bye(Map<String, Object> ctx) {
            byeCalled = true;
        }

        /** No parameter — must NOT appear in commandMap. */
        public void noParam() {}

        /** Wrong parameter type — must NOT appear in commandMap. */
        public void wrongParam(String s) {}
    }

    // ---------------------------------------------------------------------------
    // fromClass
    // ---------------------------------------------------------------------------

    @Nested
    class FromClass {

        @Test
        void commandMap_contains_exactly_the_map_accepting_methods() {
            var provider = MultiCommandProvider.fromClass(SampleCommands.class);

            assertThat(provider.getCommandMap()).containsOnlyKeys("hello", "bye");
        }

        @Test
        void getCommand_returns_non_null_for_known_names() {
            var provider = MultiCommandProvider.fromClass(SampleCommands.class);

            assertThat(provider.getCommand("hello")).isNotNull();
            assertThat(provider.getCommand("bye")).isNotNull();
        }

        @Test
        void getCommand_returns_null_for_unknown_name() {
            var provider = MultiCommandProvider.fromClass(SampleCommands.class);

            assertThat(provider.getCommand("noParam")).isNull();
            assertThat(provider.getCommand("wrongParam")).isNull();
            assertThat(provider.getCommand("nonExistent")).isNull();
        }

        @Test
        void executing_a_command_does_not_throw() {
            var provider = MultiCommandProvider.fromClass(SampleCommands.class);

            // fromClass creates its own instance per method — just verify no exception
            provider.getCommand("hello").execute();
            provider.getCommand("bye").execute();
        }
    }

    // ---------------------------------------------------------------------------
    // fromObject
    // ---------------------------------------------------------------------------

    @Nested
    class FromObject {

        @Test
        void commandMap_contains_exactly_the_map_accepting_methods() {
            var provider = MultiCommandProvider.fromObject(new SampleCommands());

            assertThat(provider.getCommandMap()).containsOnlyKeys("hello", "bye");
        }

        @Test
        void getCommand_returns_non_null_for_known_names() {
            var provider = MultiCommandProvider.fromObject(new SampleCommands());

            assertThat(provider.getCommand("hello")).isNotNull();
            assertThat(provider.getCommand("bye")).isNotNull();
        }

        @Test
        void getCommand_returns_null_for_unknown_name() {
            var provider = MultiCommandProvider.fromObject(new SampleCommands());

            assertThat(provider.getCommand("noParam")).isNull();
            assertThat(provider.getCommand("wrongParam")).isNull();
            assertThat(provider.getCommand("nonExistent")).isNull();
        }

        @Test
        void executing_hello_command_invokes_hello_on_the_original_object() {
            var target = new SampleCommands();
            var provider = MultiCommandProvider.fromObject(target);

            provider.getCommand("hello").execute();

            assertThat(target.helloCalled).isTrue();
            assertThat(target.byeCalled).isFalse();
        }

        @Test
        void executing_bye_command_invokes_bye_on_the_original_object() {
            var target = new SampleCommands();
            var provider = MultiCommandProvider.fromObject(target);

            provider.getCommand("bye").execute();

            assertThat(target.helloCalled).isFalse();
            assertThat(target.byeCalled).isTrue();
        }
    }
}
