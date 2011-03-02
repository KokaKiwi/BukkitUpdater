package org.kokakiwi.bukkitupdater;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class NullCommand extends CommandModel {

	@Override
	public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.YELLOW.getCode() + "/updater <check|update>");
	}

}
