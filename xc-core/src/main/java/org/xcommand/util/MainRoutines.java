package org.xcommand.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper routines in association with a main routine (`public static void main(String[] args)')
 */
public final class MainRoutines {
    private MainRoutines() {}

    /**
     * New `java.util.Map' from `String[] aArgs'
     */
    public static Map<String, String> newNameValueMapFromArgs(String[] aArgs) {
        Map<String, String> result = new HashMap<>();
        for (String s : aArgs) {
            int p = s.indexOf('=');
            if (p == -1) {
                continue;
            }
            String k = s.substring(0, p);
            String v = s.substring(p + 1);
            System.out.println("argument: " + k + "=" + v);
            System.out.flush();
            result.put(k, v);
        }
        return result;
    }

    /**
     * Stringvalue for `aName' or if not found `aDefault'
     * Note: `aCtx' is supposed to be the `Map' form of a `String[] aArgs' of a main routine
     */
    public static String getConfigString(Map<String, String> aCtx, String aName, String aDefault) {
        String s = aCtx.get(aName);
        return (s != null) ? s : aDefault;
    }
}
