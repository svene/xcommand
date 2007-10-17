package org.collage.dom.creationhandler;

import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Map;

public class DefaultDomNodeCreationHandlerInitializer implements IXCommand
{
	public void execute(Map aCtx)
	{
		SubjectImpl subject = new SubjectImpl();
		subject.registerObserver(new TextNodeCreationHandler());
		DomNodeCreationHandlerCV.setCreateTextNodeRequestSubject(aCtx, subject);

		subject = new SubjectImpl();
		subject.registerObserver(new VariableNodeCreationHandler());
		DomNodeCreationHandlerCV.setCreateVariableNodeRequestSubject(aCtx, subject);

		subject = new SubjectImpl();
		subject.registerObserver(new JavaNodeCreationHandler());
		DomNodeCreationHandlerCV.setCreateJavaNodeRequestSubject(aCtx, subject);

		subject = new SubjectImpl();
		subject.registerObserver(new RootNodeCreationHandler());
		DomNodeCreationHandlerCV.setCreateRootNodeRequestSubject(aCtx, subject);
	}
}
