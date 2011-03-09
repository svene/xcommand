package org.xcommand.core;

import java.util.Date;

public class Person1 implements IPerson
{

	public Person1()
	{
		delegate = DBP.newBeanForInterface(IPerson.class);
	}

	@Override
	public String getFirstName()
	{
		return delegate.getFirstName();
	}

	@Override
	public String getLastName()
	{
		return delegate.getLastName();
	}

	@Override
	public Date getBirthDate()
	{
		return delegate.getBirthDate();
	}

	@Override
	public void setFirstName(String aFirstName)
	{
		delegate.setFirstName(aFirstName);
	}

	@Override
	public void setLastName(String aLastName)
	{
		delegate.setLastName(aLastName);
	}

	@Override
	public void setBirthDate(Date aBirthDate)
	{
		delegate.setBirthDate(aBirthDate);
	}
	private final IPerson delegate;
	private static final IDynaBeanProvider DBP = DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider());
}
