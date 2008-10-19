package org.xcommand.misc.statemachine;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IResultCV;
import org.xcommand.core.IDynaBeanProvider;

public class TrueCondition implements ICommand
{
	public void execute()
	{
		resultCV.setResult(Boolean.TRUE);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IResultCV resultCV = (IResultCV) dbp.newBeanForInterface(IResultCV.class);
}
