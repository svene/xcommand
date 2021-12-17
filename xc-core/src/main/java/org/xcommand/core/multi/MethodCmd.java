package org.xcommand.core.multi;

import org.jooq.lambda.Sneaky;
import org.xcommand.core.ICommand;

import java.lang.reflect.Method;

public class MethodCmd implements ICommand
{

	public MethodCmd(Class<?> aClass)
	{
		Sneaky.runnable(() -> multiCommandObject = aClass.getDeclaredConstructor().newInstance()).run();
	}

	public MethodCmd(Object multiCommandObject)
	{
		this.multiCommandObject = multiCommandObject;
	}

	public void setMethod(Method aMethod)
	{
		method = aMethod;
	}

	@Override
	public void execute()
	{
		Sneaky.runnable(() -> method.invoke(multiCommandObject, (Object[])null)).run();
	}

	Method method;
	Object multiCommandObject;
}
