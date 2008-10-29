package org.collage.dom.evaluator.common;

import org.collage.dom.ast.IJavaCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IJavaCV javaCV = (IJavaCV) dbp.newBeanForInterface(IJavaCV.class);
}
