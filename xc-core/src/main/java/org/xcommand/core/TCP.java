package org.xcommand.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * ThreadContext Provider
 */
public class TCP
{
	private TCP() {
	}

	public static Map<String, Object> getContext()
	{
		return getStack().peek();
	}

	public static void pushContext(Map<String, Object> aCtx)
	{
		getStack().push(aCtx);
	}

	public static Map<String, Object> newInheritableContext() {
		return Factory.newInheritableMap(getContext());
	}

	public static void popContext()
	{
		getStack().pop();
	}

// --- Implementation ---

	private static Deque<Map<String, Object>> getStack()
	{
		return threadMapHolder.get();
	}

	private static final ThreadLocal<Deque<Map<String, Object>>> threadMapHolder = new ThreadLocal<>() {
		@Override
		protected synchronized Deque<Map<String, Object>> initialValue() {
			Deque<Map<String, Object>> stack = new ArrayDeque<>();
			stack.push(Factory.newInheritableMap(AC.getInstance()));
			return stack;
		}
	};
}
