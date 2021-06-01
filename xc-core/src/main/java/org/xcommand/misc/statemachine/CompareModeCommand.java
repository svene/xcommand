package org.xcommand.misc.statemachine;

import org.xcommand.core.*;
import org.xcommand.core.multi.ModeContextView;

public class CompareModeCommand implements ICommand
{

// --- Initialization ---

	public CompareModeCommand(String aMode)
	{
		this(ModeContextView.KEY_MODE, aMode);
		mode = aMode;
	}

	public CompareModeCommand(String aModeKey, String aMode)
	{
		modeKey = aModeKey;
		mode = aMode;
	}

// --- Processing ---

	@Override
	public void execute()
	{
		String ctxMode = (String) TCP.getContext().get(modeKey);
		Boolean result = mode.equals(ctxMode) ? Boolean.TRUE : Boolean.FALSE;
		resultCV.setResult(result);
	}

// --- Implementation ---

	private final String modeKey;
	private String mode;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}
