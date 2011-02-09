package org.xcommand.core;

import java.util.Date;

public class Person implements IPerson
{

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String aFirstName)
	{
		firstName = aFirstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String aLastName)
	{
		lastName = aLastName;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date aBirthDate)
	{
		birthDate = aBirthDate;
	}

	private String firstName;
	private String lastName;
	private Date birthDate;
}