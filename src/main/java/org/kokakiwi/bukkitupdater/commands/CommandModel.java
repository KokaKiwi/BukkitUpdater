package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.kokakiwi.bukkitupdater.BukkitUpdater;

public abstract class CommandModel {
	
	protected CommandSender sender;
	protected Command command;
	protected String commandLabel;
	protected String[] args;
	
	public abstract boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args, BukkitUpdater plugin);

	public CommandSender getSender() {
		return sender;
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getCommandLabel() {
		return commandLabel;
	}

	public void setCommandLabel(String commandLabel) {
		this.commandLabel = commandLabel;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
}
