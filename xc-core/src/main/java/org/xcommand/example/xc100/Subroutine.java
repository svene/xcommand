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
		Map<String, Object> ctx = new HashMap<>();
		ctx.put("name", "Sven");
		new Subroutine().execute(ctx);
	}

	private void execute(Map<String, Object> aCtx)
	{
		String name = (String) aCtx.get("name");
		Map<String, Object> ctx = new InheritableMap<>(aCtx);
		ctx.put("tmpString", "hi there");
		subExecute(ctx);
		System.out.println("after subExecute():");
		System.out.println("name = " + name);
		System.out.println("tmpString = " + aCtx.get("tmpString"));
	}

	private void subExecute(Map aCtx)
	{
		String name = (String) aCtx.get("name");
		String tmpString = (String) aCtx.get("tmpString");
		System.out.println("subExecute(): name = " + name);
		System.out.println("subExecute(): tmpString = " + tmpString);		
	}

}
