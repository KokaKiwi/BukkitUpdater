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

import org.bukkit.plugin.Plugin;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.kokakiwi.bukkitupdater.BukkitUpdater;
import org.kokakiwi.bukkitupdater.utils.VersionComparator;

public class BUpdater {
	
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.Updater");
	private final BukkitUpdater plugin;
	private ArrayList<String> repositories;
	private Map<String, BPlugin> plugins;

	public BUpdater(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
		
		if(!new File("bukkitUpdates/plugins/").exists())
			new File("bukkitUpdates/").mkdirs();
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
	public void updateLists() {
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
							plug.getChildText("author"), plug.getChildText("jar-url"));
					if(plug.getChildText("url") != null)
						bplug.url = plug.getChildText("url");
					plugins.put(plug.getChildText("id"), bplug);
				}
			} catch (JDOMException e) {
				logger.warning("BukkitUpdater : Error during parsing repository '" + repository + "'");
				e.printStackTrace();
			} catch (IOException e) {
				logger.warning("BukkitUpdater : Error during parsing repository '" + repository + "'");
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<BPlugin> check()
	{
		logger.info("BukkitUpdater : Checking updates from repository in cache...");
		ArrayList<BPlugin> checker = new ArrayList<BPlugin>();
		Plugin[] loadedPlugins = plugin.getPluginManager().getPlugins();
		for(Plugin plug : loadedPlugins)
		{
			String currentVersion = plug.getDescription().getVersion();
			String newVersion = plugins.get(plug.getDescription().getName()).version;
			String compared = VersionComparator.compare(currentVersion, newVersion);
			if(compared.equals("<"))
			{
				checker.add(plugins.get(plug.getDescription().getName()));
				logger.info("BukkitUpdater : New version detected for '" + plug.getDescription().getName() + "' (new version: " + newVersion + ")");
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
			plugin.download.download(plug.jarUrl, new File("bukkitUpdates/plugins/" + plug.jarUrl.substring(plug.jarUrl.lastIndexOf("/") + 1)));
		}
		
		return false;
	}
}
