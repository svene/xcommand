package org.xcommand.threadcontext;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class TIn2OutCommand implements ICommand
{
	public void execute()
	{
		tIn2OutCV.setOutput(tIn2OutCV.getInput());
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private ITIn2OutCV tIn2OutCV = (ITIn2OutCV) dbp.newBeanForInterface(ITIn2OutCV.class);
}
