package org.xcommand.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Command executing commands stored in a list
 */
public class ListCommand implements ICommand
{
	public ListCommand() {
	}

	public ListCommand(ICommand... aCommands) {
		this(Arrays.asList(aCommands));
	}

	public ListCommand(List<? extends ICommand> aCommands) {
		commands = aCommands;
	}

// --- Access ---

	public List<? extends ICommand> getCommands()
	{
		return commands;
	}

// --- Setting ---

	public void setCommands(List<? extends ICommand> aCommands)
	{
		commands = aCommands;
	}

// --- Processing ---

	@Override
	public void execute()
	{
		for (ICommand command : commands) {
			command.execute();
		}
	}

// --- Implementation ---

	private List<? extends ICommand> commands = new ArrayList<>();
}
