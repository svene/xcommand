package org.xcommand.threadcontext;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

public class TIn2OutCommand implements ICommand
{
	public void execute()
	{
		tIn2OutCV.setOutput(tIn2OutCV.getInput());
	}
	private DynaBeanProvider dynaBeanProvider = new DynaBeanProvider();
	private ITIn2OutCV tIn2OutCV = (ITIn2OutCV) dynaBeanProvider.getBeanForInterface(ITIn2OutCV.class);
}
