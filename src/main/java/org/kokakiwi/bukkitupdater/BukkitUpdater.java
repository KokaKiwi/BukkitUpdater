/**
 * 
 */
package org.kokakiwi.bukkitupdater;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Koka El Kiwi
 *
 */
public class BukkitUpdater extends JavaPlugin {

	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater");
	private PermissionsChecker perms;
	private PluginManager pm;
	private PluginDescriptionFile pdfFile;
	private UpdaterConfiguration config;

	public void onEnable() {
		pm = this.getServer().getPluginManager();
		perms = new PermissionsChecker(this);
		pdfFile = this.getDescription();
		config = new UpdaterConfiguration(this);
		
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
	}
	
	public PluginManager getPluginManager()
	{
		return this.pm;
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
