/**
 * 
 */
package org.kokakiwi.bukkitupdater;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Koka El Kiwi
 *
 */
public class BukkitUpdater extends JavaPlugin {

	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater");
	private final PermissionsChecker perms;
	private final PluginManager pm;
	private final PluginDescriptionFile pdfFile;
	private final UpdaterConfiguration config;
	
	public BukkitUpdater() {
		pm = this.getServer().getPluginManager();
		perms = new PermissionsChecker(this);
		pdfFile = this.getDescription();
		config = new UpdaterConfiguration(this);
	}

	public void onEnable() {
		getCommand("updater").setExecutor(new UpdaterCommandHandler(this));
		
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
	}
	
	public PluginManager getPluginManager()
	{
		return this.pm;
	}
}
