package org.xcommand.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

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
		return getStack().peek();
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

	private static Deque<Map> getStack()
	{
		return threadMapHolder.get();
	}

	private static final ThreadLocal<Deque<Map>> threadMapHolder = new ThreadLocal<Deque<Map>>()
	{
		@Override
		protected synchronized Deque<Map> initialValue()
		{
			Deque<Map> stack = new ArrayDeque<>();
			stack.push(new InheritableMap(AC.getInstance()));
			return stack;
		}
	};
}
