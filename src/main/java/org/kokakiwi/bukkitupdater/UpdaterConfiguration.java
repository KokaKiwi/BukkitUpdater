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
	
	public UpdaterConfiguration(BukkitUpdater bukkitUpdater) throws IOException
	{
		this.plugin = bukkitUpdater;
		
		if(!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdirs();
		
		if(!new File(plugin.getDataFolder(), "cache/").exists())
			new File(plugin.getDataFolder(), "cache/").mkdirs();
		
		//Load config file
		File confFile = new File(plugin.getDataFolder(), "config.yml");
		
		if(!confFile.exists())
		{
			confFile.createNewFile();
			InputStream input = BukkitUpdater.class.getResourceAsStream("/defaults/config.yml");
		        if (input != null) {
		            FileOutputStream output = null;
		
		            try {
		                output = new FileOutputStream(confFile);
		                byte[] buf = new byte[8192];
		                int length = 0;
		                while ((length = input.read(buf)) > 0) {
		                    output.write(buf, 0, length);
		                }
		
		                logger.info("BukkitUpdater : Default configuration file written.");
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
		
		//Create local repository if doesn't exists
		File localRepository = new File(plugin.getDataFolder(), "cache/local.xml");
		
		if(!localRepository.exists())
		{
			localRepository.createNewFile();
			InputStream input = BukkitUpdater.class.getResourceAsStream("/defaults/local.xml");
		        if (input != null) {
		            FileOutputStream output = null;
		
		            try {
		                output = new FileOutputStream(localRepository);
		                byte[] buf = new byte[8192];
		                int length = 0;
		                while ((length = input.read(buf)) > 0) {
		                    output.write(buf, 0, length);
		                }
		
		                logger.info("BukkitUpdater : Local repository created.");
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

	public Configuration getConfig() {
		return config;
	}
}
