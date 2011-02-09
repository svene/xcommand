package org.xcommand.core;

import java.util.Date;

public interface IPerson
{
	public String getFirstName();
	public String getLastName();
	public Date getBirthDate();

	public void setFirstName(String aFirstName);
	public void setLastName(String aLastName);
	public void setBirthDate(Date aBirthDate);
}
