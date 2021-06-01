package org.xcommand.core;

import java.util.Date;

public interface IPerson
{
	String getFirstName();
	String getLastName();
	Date getBirthDate();

	void setFirstName(String aFirstName);
	void setLastName(String aLastName);
	void setBirthDate(Date aBirthDate);
}
