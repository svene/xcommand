package org.xcommand.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Command executing commands stored in a list
 */
public class ListCommand implements ICommand {
	public ListCommand() {
	}

	public ListCommand(ICommand... aCommands) {
		this(List.of(aCommands));
	}

	public ListCommand(List<? extends ICommand> aCommands) {
		commands = aCommands;
	}

	public List<? extends ICommand> getCommands() {
		return commands;
	}

	public void setCommands(List<? extends ICommand> aCommands) {
		commands = aCommands;
	}

	@Override
	public void execute() {
		commands.forEach(ICommand::execute);
	}

	private List<? extends ICommand> commands = new ArrayList<>();
}
