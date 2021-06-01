package org.xcommand.misc.statemachine;

import org.xcommand.core.*;

public class TrueCondition implements ICommand
{
	@Override
	public void execute()
	{
		resultCV.setResult(Boolean.TRUE);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
