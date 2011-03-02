package org.kokakiwi.bukkitupdater;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.kokakiwi.bukkitupdater.commands.*;

public class UpdaterCommandHandler implements CommandExecutor {

	private final BukkitUpdater plugin;
	
	public UpdaterCommandHandler(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("updater"))
		{
			CommandModel handler = null;
			String subcommand = args[0].toLowerCase();
			
			if(subcommand.equals("update"))
				handler = new UpdateCommand();
			else if(subcommand.equals("check"))
				handler = new CheckCommand();
			else
				handler = new NullCommand();
			
			handler.execute(sender, cmd, commandLabel, args);
		}
		
		return false;
	}

}
