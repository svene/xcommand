package org.collage.dom.creationhandler;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.pattern.observer.BasicNotifier;

public class DefaultDomNodeCreationHandlerInitializer implements ICommand
{
	public void execute()
	{
		AbstractBasicNotifier notifier = new BasicNotifier();
		notifier.registerObserver(new TextNodeCreationHandler());
		domNodeCreationHandlerCV.setCreateTextNodeRequestNotifier(notifier);

		notifier = new BasicNotifier();
		notifier.registerObserver(new VariableNodeCreationHandler());
		domNodeCreationHandlerCV.setCreateVariableNodeRequestNotifier(notifier);

		notifier = new BasicNotifier();
		notifier.registerObserver(new JavaNodeCreationHandler());
		domNodeCreationHandlerCV.setCreateJavaNodeRequestNotifier(notifier);

		notifier = new BasicNotifier();
		notifier.registerObserver(new RootNodeCreationHandler());
		domNodeCreationHandlerCV.setCreateRootNodeRequestNotifier(notifier);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);

}
