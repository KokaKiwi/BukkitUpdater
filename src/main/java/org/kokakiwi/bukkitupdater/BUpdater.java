package org.kokakiwi.bukkitupdater;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BUpdater {
	
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.Updater");
	private final BukkitUpdater plugin;

	public BUpdater(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
	}
	
	public void downloadLists()
	{
		for(String url : plugin.getConfig().getUpdateUrls())
		{
			plugin.download.download(url, new File(plugin.getDataFolder(), "cache/" + url.substring(url.lastIndexOf("/") + 1)));
		}
	}
	
	public ArrayList<String> check()
	{
		return null;
	}
	
	public boolean update()
	{
		logger.info("BukkitUpdater : Updating...");
		downloadLists();
		
		return false;
	}
}
