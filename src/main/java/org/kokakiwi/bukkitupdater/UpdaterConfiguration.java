package org.kokakiwi.bukkitupdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class UpdaterConfiguration {
	
	private final BukkitUpdater plugin;
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.Configuration");
	
	private Configuration config;
	private List<String> updateUrls;
	
	public UpdaterConfiguration(BukkitUpdater bukkitUpdater)
	{
		//Load config file
		this.plugin = bukkitUpdater;
		File confFile = new File(plugin.getDataFolder(), "config.yml");
		
		if(!confFile.exists())
		{
			if (!confFile.exists()) {
				confFile.mkdirs();
	            InputStream input =
	                    BukkitUpdater.class.getResourceAsStream("/defaults/config.yml");
	            if (input != null) {
	                FileOutputStream output = null;

	                try {
	                    output = new FileOutputStream(confFile);
	                    byte[] buf = new byte[8192];
	                    int length = 0;
	                    while ((length = input.read(buf)) > 0) {
	                        output.write(buf, 0, length);
	                    }

	                    logger.info("BukkitUpdater: Default configuration file written.");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } finally {
	                    try {
	                        if (input != null) {
	                            input.close();
	                        }
	                    } catch (IOException e) {
	                    }

	                    try {
	                        if (output != null) {
	                            output.close();
	                        }
	                    } catch (IOException e) {
	                    }
	                }
	            }
	        }
			
			if(!new File(plugin.getDataFolder(), "cache/").exists())
			{
				new File(plugin.getDataFolder(), "cache/").mkdirs();
			}
		}
		
		config = new Configuration(confFile);
		config.load();
		
		updateUrls = config.getStringList("updateUrls", null);
	}
	
	public boolean set(String node, Object value)
	{
		config.setProperty(node, value);
		return config.save();
	}
	
	public Object get(String node)
	{
		return config.getProperty(node);
	}
	
	public List<String> getUpdateUrls()
	{
		return this.updateUrls;
	}
}
