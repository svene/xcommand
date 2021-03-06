package org.xcommand.misc.statemachine;

import org.xcommand.core.*;

public class FalseCondition implements ICommand
{
	@Override
	public void execute()
	{
		resultCV.setResult(Boolean.FALSE);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
