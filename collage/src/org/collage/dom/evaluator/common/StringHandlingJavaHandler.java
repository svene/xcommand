package org.collage.dom.evaluator.common;

import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.DynaBeanProvider;

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
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IJavaCV javaCV = (IJavaCV) dbp.getBeanForInterface(IJavaCV.class);
}
