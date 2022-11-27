package org.xcommand.core;

import java.util.Date;

public class Person implements IPerson {

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String aFirstName) {
		firstName = aFirstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String aLastName) {
		lastName = aLastName;
	}

	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public void setBirthDate(Date aBirthDate) {
		birthDate = aBirthDate;
	}

	private String firstName;
	private String lastName;
	private Date birthDate;
}
