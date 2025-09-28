package org.xcommand.core;

@FunctionalInterface
public interface IDynaBeanProvider {
    <T> T newBeanForInterface(Class<T> aInterface);
}
