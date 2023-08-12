package org.xcommand.misc.statemachine;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.IResultCV;

public class FalseCondition implements ICommand {
	@Override
	public void execute() {
		resultCV.setResult(Boolean.FALSE);
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
