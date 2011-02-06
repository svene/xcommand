package org.xcommand.template.jst;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import java.util.HashMap;

public class JSTInitializer implements ICommand
{
	public void execute()
	{
		jstScannerCV.setClassMap(new HashMap());
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IJSTScannerCV jstScannerCV = (IJSTScannerCV) dbp.newBeanForInterface(IJSTScannerCV.class);
}
