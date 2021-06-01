package org.collage.dom.evaluator.common;

import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class StringHandlingJavaHandler extends StringHandlingHandler
{

// --- Initialization ---

	public StringHandlingJavaHandler(StringHandlerCommand aStringHandlerCommand)
	{
		super(aStringHandlerCommand);
	}

// --- Implementation ---

	protected String getOriginalText()
	{
		return javaCV.getJava().getValue();
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}
