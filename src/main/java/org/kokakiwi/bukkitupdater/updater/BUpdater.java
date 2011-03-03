package org.kokakiwi.bukkitupdater.updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.kokakiwi.bukkitupdater.BukkitUpdater;
import org.kokakiwi.bukkitupdater.utils.UnZip;
import org.kokakiwi.bukkitupdater.utils.VersionComparator;

public class BUpdater {
	
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.Updater");
	private final BukkitUpdater plugin;
	private ArrayList<String> repositories;
	private Map<String, BPlugin> plugins;

	public BUpdater(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
		
		if(!new File("bukkitUpdates/plugins/").exists())
			new File("bukkitUpdates/plugins/").mkdirs();
		
		if(!new File("bukkitUpdates/archives/").exists())
			new File("bukkitUpdates/archives/").mkdirs();
	}
	
	public void downloadLists()
	{
		repositories = new ArrayList<String>();
		for(String url : plugin.getConfig().getUpdateUrls())
		{
			plugin.download.download(url, new File(plugin.getDataFolder(), "cache/" + url.substring(url.lastIndexOf("/") + 1)));
			repositories.add(url.substring(url.lastIndexOf("/") + 1));
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateLists() {
		plugins = new HashMap<String, BPlugin>();
		
		for(String repository : repositories)
		{
			SAXBuilder sxb = new SAXBuilder();
			try {
				Document document = sxb.build(new File(plugin.getDataFolder(), "cache/" + repository));
				Element rootNode = document.getRootElement();
				
				List<Element> repoPlugins = rootNode.getChild("plugins").getChildren("plugin");
				Iterator<Element> i = repoPlugins.iterator();
				while(i.hasNext())
				{
					Element plug = i.next();
					BPlugin bplug = new BPlugin(plug.getChildText("id"), plug.getChildText("name"), plug.getChildText("version"),
							plug.getChildText("author"), plug.getChildText("file-type"), plug.getChildText("file-url"));
					if(plug.getChildText("url") != null)
						bplug.url = plug.getChildText("url");
					if(bplug.fileType.equalsIgnoreCase("archive"))
					{
						bplug.archive = plug.getChild("archive");
					}
					plugins.put(plug.getChildText("id"), bplug);
				}
				return true;
			} catch (JDOMException e) {
				logger.warning("BukkitUpdater : Error during parsing repository '" + repository + "'");
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				logger.warning("BukkitUpdater : Error during parsing repository '" + repository + "'");
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public ArrayList<BPlugin> check()
	{
		logger.info("BukkitUpdater : Checking updates from repository in cache...");
		ArrayList<BPlugin> checker = new ArrayList<BPlugin>();
		Plugin[] loadedPlugins = plugin.getPluginManager().getPlugins();
		for(Plugin plug : loadedPlugins)
		{
			if(plugins.get(plug.getDescription().getName()) != null)
			{
				String currentVersion = plug.getDescription().getVersion();
				String newVersion = plugins.get(plug.getDescription().getName()).version;
				String compared = VersionComparator.compare(currentVersion, newVersion);
				if(compared.equals("<"))
				{
					checker.add(plugins.get(plug.getDescription().getName()));
					logger.info("BukkitUpdater : New version detected for '" + plug.getDescription().getName() + "' (new version: " + newVersion + ")");
				}
			}else{
				logger.info("BukkitUpdater : Plugin '" + plug.getDescription().getName() + "' isn't in loaded repositories");
			}
		}
		return checker;
	}
	
	public boolean update()
	{
		logger.info("BukkitUpdater : Updating...");
		downloadLists();
		updateLists();
		ArrayList<BPlugin> newPlugins = check();
		for(BPlugin plug : newPlugins)
		{
			if(plug.fileType.equalsIgnoreCase("jar"))
				plugin.download.download(plug.fileUrl, new File("bukkitUpdates/plugins/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)));
			else if(plug.fileType.equalsIgnoreCase("archive"))
			{
				plugin.download.download(plug.fileUrl, new File("bukkitUpdates/archives/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)));
				UnZip.unzip(new File("bukkitUpdates/archives/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)), new File("bukkitUpdates/plugins/"));
			}
		}
		
		return true;
	}
	
	public String install(String id)
	{
		if(plugins.get(id) != null)
		{
			BPlugin plug = plugins.get(id);
			if(plugin.getPluginManager().getPlugin(plug.name) == null)
			{
				if(plug.fileType.equalsIgnoreCase("jar"))
					plugin.download.download(plug.fileUrl, new File("plugins/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)));
				else if(plug.fileType.equalsIgnoreCase("archive"))
				{
					plugin.download.download(plug.fileUrl, new File("bukkitUpdates/archives/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)));
					UnZip.unzip(new File("bukkitUpdates/archives/" + plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1)), new File("plugins/"));
				}
				return "Plugin installed!";
			}else{
				return "Plugin already installed!";
			}
		}else{
			return "Plugin didn't exists!";
		}
	}
	
	public String remove(String id)
	{
		if(plugins.get(id) != null)
		{
			BPlugin plug = plugins.get(id);
			if(plugin.getPluginManager().getPlugin(plug.name) != null)
			{
				if(plugin.getPluginManager().isPluginEnabled(plug.name))
				{
					plugin.getPluginManager().disablePlugin(plugin.getPluginManager().getPlugin(plug.name));
				}
				String jarName = plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1);
				new File("plugins/", jarName).delete();
				return "Plugin removed!";
			}else{
				return "Plugin isn't loaded!";
			}
		}else{
			return "Plugin didn't exists!";
		}
	}
	
	public String purge(String id)
	{
		if(plugins.get(id) != null)
		{
			BPlugin plug = plugins.get(id);
			if(plugin.getPluginManager().getPlugin(plug.name) != null)
			{
				if(plugin.getPluginManager().isPluginEnabled(plug.name))
				{
					plugin.getPluginManager().disablePlugin(plugin.getPluginManager().getPlugin(plug.name));
				}
				String jarName = plug.fileUrl.substring(plug.fileUrl.lastIndexOf("/") + 1);
				plugin.getPluginManager().getPlugin(plug.name).getDataFolder().delete();
				new File("plugins/", jarName).delete();
				return "Plugin purged!";
			}else{
				return "Plugin isn't installed!";
			}
		}else{
			return "Plugin didn't exists!";
		}
	}
	
	public String enable(String arg)
	{
		if(plugins.get(arg) != null)
		{
			plugin.getPluginManager().enablePlugin(plugin.getPluginManager().getPlugin(plugins.get(arg).name));
			return "Plugin enabled.";
		}else if(plugin.getPluginManager().getPlugin(arg) != null)
		{
			plugin.getPluginManager().enablePlugin(plugin.getPluginManager().getPlugin(arg));
			return "Plugin enabled.";
		}else{
			return "Plugin didn't exists!";
		}
	}
	
	public String disable(String arg)
	{
		if(plugins.get(arg) != null)
		{
			plugin.getPluginManager().disablePlugin(plugin.getPluginManager().getPlugin(plugins.get(arg).name));
			return "Plugin disabled.";
		}else if(plugin.getPluginManager().getPlugin(arg) != null)
		{
			plugin.getPluginManager().disablePlugin(plugin.getPluginManager().getPlugin(arg));
			return "Plugin disabled.";
		}else{
			return "Plugin didn't exists!";
		}
	}
	
	public String load(String arg)
	{
		if(plugins.get(arg) != null)
		{
			File jarFile = new File("plugins/", plugins.get(arg).fileUrl.substring(plugins.get(arg).fileUrl.lastIndexOf("/") + 1));
			return "Plugin disabled.";
		}else {
			if(new File("plugins/", arg + ".jar").exists())
			{
				try {
					plugin.getPluginManager().loadPlugin(new File("plugins/", arg + ".jar"));
					return "Plugin loaded.";
				} catch (Exception e) {
					logger.severe("Error during loading plugin '" + arg + "' !");
					e.printStackTrace();
					return "Error during loading plugin.";
				}
			}else {
				return "Plugin not found.";
			}
		}
	}
}
