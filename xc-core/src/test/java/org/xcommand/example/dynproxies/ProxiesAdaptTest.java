package org.xcommand.example.dynproxies;

import static org.assertj.core.api.Assertions.assertThat;

import eu.javaspecialists.books.dynamicproxies.Proxies;
import org.junit.jupiter.api.Test;

public class ProxiesAdaptTest {

    @Test
    void lenghtNotProxied() {
        String orgString = "Good morning Vietnam";
        String patchString = "Kilroy";
        CharSequence seq = Proxies.adapt(CharSequence.class, orgString, new Object() {
            @Override
            public String toString() {
                return patchString;
            }

            @SuppressWarnings({"unused", "EffectivelyPrivate"})
            public int length() {
                return 42;
            }
        });
        assertThat(seq.toString()).isEqualTo(patchString);
        assertThat(seq.length()).isEqualTo(orgString.length());
    }

    public interface LengthProvider {
        int length();
    }

    @Test
    void lenghtProxiedCorrectly() {
        String orgString = "Good morning Vietnam";
        String patchString = "Kilroy";
        CharSequence seq = Proxies.adapt(CharSequence.class, orgString, new LengthProvider() {
            @Override
            public String toString() {
                return patchString;
            }

            @Override
            public int length() {
                return 42;
            }
        });
        assertThat(seq.toString()).isEqualTo(patchString);
        assertThat(seq.length()).isEqualTo(42);
    }
}
