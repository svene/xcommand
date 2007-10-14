package org.collage.dom.ast;

import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.pattern.observer.ISubject;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Map;
import java.util.HashMap;

public class DomEventHandlerProvider extends MapBasedHandlerProvider
{

// --- Initialization ---

	public DomEventHandlerProvider()
	{
		Map map = new HashMap();
		map.put(RootNode.class, rootSubject);
		map.put(Text.class, textSubject);
		map.put(Variable.class, variableSubject);
		map.put(Java.class, javaSubject);
		setHandlerMap(map);
	}

// --- Access ---

	public ISubject getRootSubject()
	{
		return rootSubject;
	}

	public ISubject getTextSubject()
	{
		return textSubject;
	}

	public ISubject getVariableSubject()
	{
		return variableSubject;
	}

	public ISubject getJavaSubject()
	{
		return javaSubject;
	}

// --- Implementation ---

	 private ISubject rootSubject = new SubjectImpl();
	 private ISubject textSubject = new SubjectImpl();
	 private ISubject variableSubject = new SubjectImpl();
	 private ISubject javaSubject = new SubjectImpl();

}
