package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kokakiwi.bukkitupdater.BukkitUpdater;

public class ListCommand extends CommandModel {

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args, BukkitUpdater plugin) {
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
		
		if(args.length != 2)
			return false;
		
		if(sender instanceof Player)
		{
			if(plugin.perms.has((Player) sender, "updater.list"))
			{
				String message = plugin.updater.list(args[1]);
				sender.sendMessage(ChatColor.GRAY.getCode() + message);
				return true;
			}else {
				sender.sendMessage(ChatColor.RED.getCode() + "You're not permitted to use this command!");
				return false;
			}
		}else{
			String message = plugin.updater.list(args[1]);
			sender.sendMessage(message);
			return true;
		}
	}

}
