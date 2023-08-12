package org.xcommand.core;

import java.util.HashMap;

/**
 * ApplicationContext. Map using a static instance to store application wide data.
 * Disadvantage: using a static variable to store the data will cause problems when
 * the same classloader is used for different applications. This can be the case when
 * for example several webapps run in one JVM and the jar containing this class is attached
 * to parent classloader of the server processs. Then each webapp share this class and thus
 * share the same global ApplicationContext which is not what you want.
 * It is possible to work around this by putting the jar containing this class inside WEB-INF/lib
 * which has the effect that it is not loaded by a shared classloader but by the classloader
 * of the webapp.
 * But clearly this is an open design issue of this class which needs to be resolved. 
 */
public final class AC extends HashMap<String, Object>
{

	private AC() {
	}

	public static AC getInstance() {
		return instance;
	}

	private static final AC instance = new AC();
}
