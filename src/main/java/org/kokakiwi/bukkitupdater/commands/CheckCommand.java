package org.kokakiwi.bukkitupdater.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CheckCommand extends CommandModel {

	@Override
	public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		this.sender = sender;
		this.command = cmd;
		this.commandLabel = commandLabel;
		this.args = args;
	}

}
