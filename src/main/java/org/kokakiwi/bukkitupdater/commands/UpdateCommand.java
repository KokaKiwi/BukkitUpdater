package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.ChatColor;
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
		
		if(sender instanceof Player)
		{
			if(plugin.perms.has((Player) sender, "updater.update"))
			{
				if(plugin.updater.update())
					sender.sendMessage(ChatColor.GRAY.getCode() + "Plugins updated! Check bukkitUpdates dir!");
				else
					sender.sendMessage(ChatColor.RED.getCode() + "Error during update");
				return true;
			}else {
				sender.sendMessage(ChatColor.RED.getCode() + "You're not permitted to use this command!");
				return false;
			}
		}else{
			if(plugin.updater.update())
				sender.sendMessage("BukkitUpdater : Plugins updated! Check bukkitUpdates dir!");
			else
				sender.sendMessage("BukkitUpdater : Error during update");
			return true;
		}
	}
}
