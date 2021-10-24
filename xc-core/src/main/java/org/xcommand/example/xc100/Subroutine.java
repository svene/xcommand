package org.xcommand.example.xc100;

import org.xcommand.core.InheritableMap;

import java.util.Map;
import java.util.HashMap;

/**
 * This example demonstrates how an `InheritableMap' can be used
 * to store temporary arguments for subroutines. This is useful, when only the
 * subroutine (subExecute in this example) needs access to some variables ('tmpString'
 * in this example) but not following processes. This way the temporary variables do not
 * pollute the context.
 */
public class Subroutine
{
	public static void main(String[] args)
	{
		Map<String, String> ctx = new HashMap<>();
		ctx.put("name", "Sven");
		new Subroutine().execute(ctx);
	}

	private void execute(Map<String, String> aCtx)
	{
		String name = aCtx.get("name");
		Map<String, String> ctx = new InheritableMap<>(aCtx);
		ctx.put("tmpString", "hi there");
		subExecute(ctx);
		System.out.println("after subExecute():");
		System.out.println("name = " + name);
		System.out.println("tmpString = " + aCtx.get("tmpString"));
	}

	private void subExecute(Map<String, String> aCtx)
	{
		String name = aCtx.get("name");
		String tmpString = aCtx.get("tmpString");
		System.out.println("subExecute(): name = " + name);
		System.out.println("subExecute(): tmpString = " + tmpString);		
	}

}
