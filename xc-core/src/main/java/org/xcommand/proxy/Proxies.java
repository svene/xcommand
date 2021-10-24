package org.xcommand.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class Proxies {
	private Proxies() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T newProxy(Class<? super T> interfaze, InvocationHandler handler) {
		Objects.requireNonNull(interfaze);
		Objects.requireNonNull(handler);

		return (T)Proxy.newProxyInstance(
			interfaze.getClassLoader(),
			new Class<?>[] {interfaze},
			handler
		);
	}
}
