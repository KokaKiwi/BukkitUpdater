/**
 * 
 */
package org.kokakiwi.bukkitupdater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kokakiwi.bukkitupdater.commands.*;
import org.kokakiwi.bukkitupdater.updater.BUpdater;
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
	public UpdaterConfiguration config;
	public BUpdater updater;
	public FileDownloader download = new FileDownloader(this);

	public void onEnable() {
		if(!new File("lib/").exists())
			new File("lib/").mkdirs();
		
		if(!new File("lib/jdom-1.1.jar").exists())
		{
			logger.warning("BukkitUpdater : jDom library not found, downloading it...");
			download.download("http://kokakiwi.github.com/jdom-1.1.jar", new File("lib/jdom-1.1.jar"));
			logger.info("BukkitUpdater : jDom library v1.1 downloaded!");
		}
		
		pm = this.getServer().getPluginManager();
		perms = new PermissionsChecker(this);
		pdfFile = this.getDescription();
		try {
			config = new UpdaterConfiguration(this);
		} catch (IOException e) {
			logger.severe("BukkitUpdater : Error during config loading!");
			pm.disablePlugin(this);
		}
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
			else if(subcommand.equalsIgnoreCase("install"))
				handler = new InstallCommand();
			else if(subcommand.equalsIgnoreCase("remove"))
				handler = new RemoveCommand();
			else if(subcommand.equalsIgnoreCase("purge"))
				handler = new PurgeCommand();
			else if(subcommand.equalsIgnoreCase("disable"))
				handler = new DisableCommand();
			else if(subcommand.equalsIgnoreCase("enable"))
				handler = new EnableCommand();
			else if(subcommand.equalsIgnoreCase("load"))
				handler = new LoadCommand();
			else
				handler = new NullCommand();
			
			return handler.execute(sender, cmd, commandLabel, args, this);
		}
		
		return false;
	}
}
