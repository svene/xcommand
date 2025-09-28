package org.xcommand.core;

/**
 * IXCommands doing nothing (Nop: no operation)
 */
public class NopCommand implements ICommand {
    @Override
    public final void execute() {}
}
