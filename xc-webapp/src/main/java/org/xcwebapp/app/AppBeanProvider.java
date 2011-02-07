package org.xcwebapp.app;

import org.xcommand.core.ListCommand;
import org.xcommand.core.ICommand;

public class AppBeanProvider
{

// --- Access ---

	public ListCommand getXcServletContextListenerCommand()
	{
		return xcServletContextListenerCommand;
	}

	public ICommand getRequestDispatcherCommand()
	{
		return requestDispatcherCommand;
	}
// --- Setting ---

	public void setXcServletContextListenerCommand(ListCommand aXcServletContextListenerCommand)
	{
		xcServletContextListenerCommand = aXcServletContextListenerCommand;
	}

	public void setRequestDispatcherCommand(ICommand aRequestDispatcherCommand)
	{
		requestDispatcherCommand = aRequestDispatcherCommand;
	}
// --- Implementation ---

	private ListCommand xcServletContextListenerCommand;
	private ICommand requestDispatcherCommand;
}
