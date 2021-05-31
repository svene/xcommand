package org.xcommand.misc.statemachine;

import org.xcommand.core.*;

public class TrueCondition implements ICommand
{
	public void execute()
	{
		resultCV.setResult(Boolean.TRUE);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
