package org.xcommand.core;

import org.xcommand.misc.AC;
import org.xcommand.misc.InheritableMap;

import java.util.Map;
import java.util.Stack;

/**
 * ThreadContext Provider
 */
public class TCP
{
	private TCP()
	{
	}
	public static Map getContext()
	{
		return (Map) getStack().peek();
	}

	public static void pushContext(Map aCtx)
	{
		getStack().push(aCtx);
	}
	public static void pushNewInheritableContext()
	{
		getStack().push(new InheritableMap(getContext()));
	}
	public static void popContext()
	{
		getStack().pop();
	}

// --- Implementation ---

	private static Stack getStack()
	{
		return (Stack) threadMapHolder.get();
	}

	private static ThreadLocal threadMapHolder = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			Stack stack = new Stack();
			stack.push(new InheritableMap(AC.getInstance()));
			return stack;
		}
	};
}
