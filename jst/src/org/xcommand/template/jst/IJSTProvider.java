package org.xcommand.template.jst;

import org.xcommand.pattern.observer.INotifier;

import java.util.Map;

public interface IJSTProvider
{
	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname);
	public Map getClassMap();

	public INotifier getChangeNotifier();
}
