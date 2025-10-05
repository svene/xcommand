package org.xcommand.core;

import java.time.LocalDate;

public class DelegatingPerson implements IPerson {

    public DelegatingPerson(IPerson aDelegate) {
        delegate = aDelegate;
    }

    @Override
    public String getFirstName() {
        return delegate.getFirstName();
    }

    @Override
    public String getLastName() {
        return delegate.getLastName();
    }

    @Override
    public LocalDate getBirthDate() {
        return delegate.getBirthDate();
    }

    @Override
    public void setFirstName(String aFirstName) {
        delegate.setFirstName(aFirstName);
    }

    @Override
    public void setLastName(String aLastName) {
        delegate.setLastName(aLastName);
    }

    @Override
    public void setBirthDate(LocalDate aBirthDate) {
        delegate.setBirthDate(aBirthDate);
    }

    private final IPerson delegate;
}
