package org.xcommand.core;

@FunctionalInterface
public interface ResultCommand<R> {
    R execute();
}
