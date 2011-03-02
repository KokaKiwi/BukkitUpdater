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
import org.kokakiwi.bukkitupdater.commands.*;
import org.kokakiwi.bukkitupdater.utils.FileDownloader;

/**
 * @author Koka El Kiwi
 *
 */
public class BukkitUpdater extends JavaPlugin {

	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater");
	public PermissionsChecker perms;
	private PluginManager pm;
	private PluginDescriptionFile pdfFile;
	private UpdaterConfiguration config;
	private BUpdater updater;
	public FileDownloader download = new FileDownloader(this);

	public void onEnable() {
		pm = this.getServer().getPluginManager();
		perms = new PermissionsChecker(this);
		pdfFile = this.getDescription();
		config = new UpdaterConfiguration(this);
		updater = new BUpdater(this);
		
		updater.update();
		
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
	}
	
	public PluginManager getPluginManager()
	{
		return this.pm;
	}
	
	public UpdaterConfiguration getConfig()
	{
		return this.config;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String cmdName = cmd.getName();
		
		if(cmdName.equalsIgnoreCase("updater"))
		{
			CommandModel handler = null;
			
			if(args.length == 0)
			{
				handler = new NullCommand();
				return handler.execute(sender, cmd, commandLabel, args, this);
			}
			
			String subcommand = args[0];
			
			if(subcommand.equalsIgnoreCase("update"))
				handler = new UpdateCommand();
			else if(subcommand.equalsIgnoreCase("check"))
				handler = new CheckCommand();
			else
				handler = new NullCommand();
			
			return handler.execute(sender, cmd, commandLabel, args, this);
		}
		
		return false;
	}
}
