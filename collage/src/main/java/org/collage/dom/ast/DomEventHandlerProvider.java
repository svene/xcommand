package org.collage.dom.ast;

import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.util.HashMap;
import java.util.Map;

public class DomEventHandlerProvider extends MapBasedHandlerProvider
{

// --- Initialization ---

	public DomEventHandlerProvider()
	{
		Map<Object, INotifier> map = new HashMap<Object, INotifier>();
		map.put(RootNode.class, new BasicNotifier());
		map.put(Text.class, new BasicNotifier());
		map.put(Variable.class, new BasicNotifier());
		map.put(Java.class, new BasicNotifier());
		setHandlerMap(map);
	}

// --- Access ---

	public INotifier getRootNotifier()
	{
		return (INotifier) getHandler(RootNode.class);
	}

	public INotifier getTextNotifier()
	{
		return (INotifier) getHandler(Text.class);
	}

	public INotifier getVariableNotifier()
	{
		return (INotifier) getHandler(Variable.class);
	}

	public INotifier getJavaNotifier()
	{
		return (INotifier) getHandler(Java.class);
	}

// --- Implementation ---

}
