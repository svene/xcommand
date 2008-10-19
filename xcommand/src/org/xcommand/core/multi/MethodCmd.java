package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.lang.reflect.Method;

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

	public void execute()
	{
		try
		{
			method.invoke(multiCommandObject, null);
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
