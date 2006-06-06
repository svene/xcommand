package org.xcommand.multi;

import java.lang.reflect.Method;
import java.util.Map;

import org.xcommand.ICommand;

public class MethodCmd implements ICommand
{
// --- Initialization ---

	public MethodCmd(Class aClass)
	{
		try
		{
			multiCommandObject = aClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public MethodCmd(Object aMultiCommandObject)
	{
		multiCommandObject = aMultiCommandObject;
	}

// --- Setting ---

	public void setMethod(Method aMethod)
	{
		method = aMethod;
	}

// --- Processing ---

	public void execute(Map aContext)
	{
		try
		{
			method.invoke(multiCommandObject, new Object[]{aContext});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

// --- Implementation ---

	Method method;
	Object multiCommandObject;
}
