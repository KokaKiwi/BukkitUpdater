package org.kokakiwi.bukkitupdater;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UpdateCommand extends CommandModel {
	
	public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
	}
}
