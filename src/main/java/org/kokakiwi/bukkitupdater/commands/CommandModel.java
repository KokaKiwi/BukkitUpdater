package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandModel {
	
	protected CommandSender sender;
	protected Command command;
	protected String commandLabel;
	protected String[] args;
	
	public abstract boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args);
}
