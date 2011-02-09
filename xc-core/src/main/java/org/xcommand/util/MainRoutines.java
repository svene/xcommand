package org.xcommand.util;

import java.util.Map;
import java.util.HashMap;

/**
 * Helper routines in association with a main routine (`public static void main(String[] args)')
 */
public class MainRoutines
{
	/** New `java.util.Map' from `String[] aArgs'*/
	public static Map newNameValueMapFromArgs(String[] aArgs)
	{
		Map result = new HashMap();
		for (int i = 0, n = aArgs.length; i < n; i++)
		{
			String s = aArgs[i];
			int p = s.indexOf('=');
			if (p == -1) continue;
			String k = s.substring(0, p);
			String v = s.substring(p + 1, s.length());
			System.out.println("argument: " + k + "=" + v);
			System.out.flush();
			result.put(k, v);
		}
		return result;
	}

	/** Stringvalue for `aName' or if not found `aDefault'
	 * Note: `aCtx' is supposed to be the `Map' form of a `String[] aArgs' of a main routine
	 */
	public static String getConfigString(Map aCtx, String aName, String aDefault)
	{
		String s = (String) aCtx.get(aName);
		return (s != null) ? s : aDefault;
	}

}
