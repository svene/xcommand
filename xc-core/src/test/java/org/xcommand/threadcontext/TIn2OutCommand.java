package org.xcommand.threadcontext;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

public class TIn2OutCommand implements ICommand
{
	@Override
	public void execute()
	{
		tIn2OutCV.setOutput(tIn2OutCV.getInput());
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private final ITIn2OutCV tIn2OutCV = dbp.newBeanForInterface(ITIn2OutCV.class);
}
