package org.xcommand.example.xc100;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.Factory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * This example demonstrates how the xcommand pattern can be used for recursive calls.
 * For each recursive call a new inheritable Map is created so that a new 'argument stack entry'
 * is created holding the arguments ('level' in this example).
 * Thus all the previous variables are left untouched (as you can see from the variable 'name').
 */
public class Recursion
{

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args)
	{
		Map<String, Object> ctx = new HashMap<>();
		String level = "";
		ctx.put("name", "Sven");
		ctx.put("level", level);
		new Recursion().execute(ctx);
	}

	private void execute(Map<String, Object> aCtx)
	{
		String name = (String) aCtx.get("name");
		String level = (String) aCtx.get("level");
		LOGGER.info("before call : name={}, level={}", name, level);
		if (level.length() < 5)
		{
			Map<String, Object> ctx = Factory.newInheritableMap(aCtx);
			level += "#";
			ctx.put("name", ctx.get("name") + String.valueOf(level.length()));
			ctx.put("level", level);
			execute(ctx);
		}
		LOGGER.info("after call  : name={}, level={}", name, level);
	}

}
