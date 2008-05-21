package org.collage.dom.creationhandler;

import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.INotifier;

public interface IDomNodeCreationHandlerCV
{
	public String getValue();
	public ICommand getDomNodeCreationHandler();
	public Boolean getProduceJavaSource();
	public INotifier getCreateTextNodeRequestNotifier();
	public INotifier getCreateVariableNodeRequestNotifier();
	public INotifier getCreateJavaNodeRequestNotifier();
	public INotifier getCreateRootNodeRequestNotifier();
	public void setValue(String aValue);
	public void setProduceJavaSource(Boolean aProduceJavaSource);
	public void setCreateTextNodeRequestNotifier(INotifier aNotifier);
	/** @deprecated */
	public void setDomNodeCreationHandler(ICommand aDomNodeCreationHandler);
	public void setCreateVariableNodeRequestNotifier(INotifier aNotifier);
	public void setCreateJavaNodeRequestNotifier(INotifier aNotifier);
	public void setCreateRootNodeRequestNotifier(INotifier aNotifier);

}
