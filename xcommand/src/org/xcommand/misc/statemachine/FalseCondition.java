package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IResultCV;

public class FalseCondition implements ICommand
{
	public void execute()
	{
		resultCV.setResult(Boolean.FALSE);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IResultCV resultCV = (IResultCV) dbp.getBeanForInterface(IResultCV.class);
}
