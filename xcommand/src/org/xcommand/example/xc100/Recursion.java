package org.xcommand.example.xc100;

import org.xcommand.core.InheritableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * This example demonstrates how the xcommand pattern can be used for recursive calls.
 * For each recursive call a new InheritableMap is created so that a new 'argument stack entry'
 * is created holding the arguments ('level' in this example).
 * Thus all the previous variables are left untouched (as you can see from the variable 'name').
 */
public class Recursion
{

	public static void main(String[] args)
	{
		Map ctx = new HashMap();
		String level = "";
		ctx.put("name", "Sven");
		ctx.put("level", level);
		new Recursion().execute(ctx);
	}

	private void execute(Map aCtx)
	{
		String name = (String) aCtx.get("name");
		String level = (String) aCtx.get("level");
		System.out.println("before call: name=" + name + ", level=" + level);
		if (level.length() < 5)
		{
			Map ctx = new InheritableMap(aCtx);
			level = level + "#";
			ctx.put("level", level);
			execute(ctx);
		}
		System.out.println("after call : name=" + name + ", level=" + level);
	}

}
