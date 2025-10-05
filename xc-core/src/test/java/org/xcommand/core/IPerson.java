package org.xcommand.core;

import java.time.LocalDate;

public interface IPerson {
    String getFirstName();

    String getLastName();

    LocalDate getBirthDate();

    void setFirstName(String aFirstName);

    void setLastName(String aLastName);

    void setBirthDate(LocalDate aBirthDate);
}
