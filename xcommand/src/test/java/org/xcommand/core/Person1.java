package org.xcommand.core;

import java.util.Date;

public class Person1 implements IPerson
{

	public Person1()
	{
		delegate = (IPerson) DBP.newBeanForInterface(IPerson.class);
	}

	public String getFirstName()
	{
		return delegate.getFirstName();
	}

	public String getLastName()
	{
		return delegate.getLastName();
	}

	public Date getBirthDate()
	{
		return delegate.getBirthDate();
	}

	public void setFirstName(String aFirstName)
	{
		delegate.setFirstName(aFirstName);
	}

	public void setLastName(String aLastName)
	{
		delegate.setLastName(aLastName);
	}

	public void setBirthDate(Date aBirthDate)
	{
		delegate.setBirthDate(aBirthDate);
	}
	private final IPerson delegate;
	private static final IDynaBeanProvider DBP = DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider());
}
