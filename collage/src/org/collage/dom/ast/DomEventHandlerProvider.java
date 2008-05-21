package org.collage.dom.ast;

import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.pattern.observer.BasicNotifier;

import java.util.Map;
import java.util.HashMap;

public class DomEventHandlerProvider extends MapBasedHandlerProvider
{

// --- Initialization ---

	public DomEventHandlerProvider()
	{
		Map map = new HashMap();
		map.put(RootNode.class, rootNotifier);
		map.put(Text.class, textNotifier);
		map.put(Variable.class, variableNotifier);
		map.put(Java.class, javaNotifier);
		setHandlerMap(map);
	}

// --- Access ---

	public INotifier getRootNotifier()
	{
		return rootNotifier;
	}

	public INotifier getTextNotifier()
	{
		return textNotifier;
	}

	public INotifier getVariableNotifier()
	{
		return variableNotifier;
	}

	public INotifier getJavaNotifier()
	{
		return javaNotifier;
	}

// --- Implementation ---

	 private INotifier rootNotifier = new BasicNotifier();
	 private INotifier textNotifier = new BasicNotifier();
	 private INotifier variableNotifier = new BasicNotifier();
	 private INotifier javaNotifier = new BasicNotifier();

}
