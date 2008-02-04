package org.xcommand.template.jst;

import org.xcommand.pattern.observer.ISubject;

import java.util.Map;

public interface IJSTProvider
{
	public void initialize(Map aCtx);	
	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname);
	public Map getClassMap();

	public ISubject getChangeNotifier();
}
