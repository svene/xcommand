package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IResultCV;
import org.xcommand.core.IDynaBeanProvider;

public class FalseCondition implements ICommand
{
	public void execute()
	{
		resultCV.setResult(Boolean.FALSE);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IResultCV resultCV = (IResultCV) dbp.newBeanForInterface(IResultCV.class);
}
