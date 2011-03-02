package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UpdateCommand extends CommandModel {
	
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
		
		return false;
	}
}
