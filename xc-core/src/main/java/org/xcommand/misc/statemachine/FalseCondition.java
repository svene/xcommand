package org.xcommand.misc.statemachine;

import org.xcommand.core.*;

public class FalseCondition implements ICommand
{
	public void execute()
	{
		resultCV.setResult(Boolean.FALSE);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
