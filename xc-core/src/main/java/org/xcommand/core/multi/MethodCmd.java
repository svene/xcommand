package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.lang.reflect.Method;

public class MethodCmd implements ICommand
{
// --- Initialization ---

	public MethodCmd(Class<?> aClass)
	{
		try
		{
			multiCommandObject = aClass.getDeclaredConstructor().newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public MethodCmd(Object multiCommandObject)
	{
		this.multiCommandObject = multiCommandObject;
	}

// --- Setting ---

	public void setMethod(Method aMethod)
	{
		method = aMethod;
	}

// --- Processing ---

	@Override
	public void execute()
	{
		try
		{
			method.invoke(multiCommandObject, (Object[])null);
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
