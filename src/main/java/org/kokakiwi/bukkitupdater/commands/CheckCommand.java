package org.kokakiwi.bukkitupdater.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kokakiwi.bukkitupdater.BukkitUpdater;
import org.kokakiwi.bukkitupdater.updater.BPlugin;

public class CheckCommand extends CommandModel {

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args, BukkitUpdater plugin) {
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
		
		if(sender instanceof Player)
		{
			if(plugin.perms.has((Player) sender, "updater.check"))
			{
				ArrayList<BPlugin> toUpdate = plugin.updater.check();
				if(toUpdate.size() > 0)
				{
					String pluginsOutdated = "";
					for(BPlugin plug : toUpdate)
					{
						pluginsOutdated += plug.name + ", ";
					}
					sender.sendMessage(ChatColor.GRAY.toString() + "Those plugins can be updated: " + pluginsOutdated);
				}else {
					sender.sendMessage(ChatColor.GRAY.toString() + "There's no updates available.");
				}
				return true;
			}else {
				sender.sendMessage(ChatColor.RED.toString() + "You're not permitted to use this command!");
				return false;
			}
		}else{
			ArrayList<BPlugin> toUpdate = plugin.updater.check();
			if(toUpdate.size() > 0)
			{
				String pluginsOutdated = "";
				for(BPlugin plug : toUpdate)
				{
					pluginsOutdated += plug.name + ", ";
				}
				sender.sendMessage("BukkitUpdater : Those plugins can be updated: " + pluginsOutdated);
			}else {
				sender.sendMessage("BukkitUpdater : There's no updates available.");
			}
			return true;
		}
	}

}
