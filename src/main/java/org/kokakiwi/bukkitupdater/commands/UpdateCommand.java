package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kokakiwi.bukkitupdater.BukkitUpdater;

public class UpdateCommand extends CommandModel {
	
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args, BukkitUpdater plugin)
	{
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
		
		return false;
	}
}
