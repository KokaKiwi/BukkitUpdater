package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;
import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class Updater
{
    private final BukkitUpdater        plugin;
    private final ConfigurationSection config;
    private File                       tempDir      = null;
    private File                       installDir   = null;
    private File                       pluginsDir   = null;
    
    private final List<Repository>     repositories = new ArrayList<Repository>();
    
    public Updater(BukkitUpdater plugin, ConfigurationSection config)
    {
        this.plugin = plugin;
        this.config = config;
    }
    
    public void initialize()
    {
        load();
    }
    
    // Actions
    
    public List<PluginEntry> update()
    {
        final List<PluginEntry> results = new ArrayList<PluginEntry>();
        
        return results;
    }
    
    public boolean install(PluginEntry entry) throws Exception
    {
        return PluginInstaller.install(entry, installDir, tempDir);
    }
    
    public boolean delete(PluginEntry entry)
    {
        return delete(entry, false);
    }
    
    @SuppressWarnings("unchecked")
    public boolean delete(PluginEntry entry, boolean purge)
    {
        final Plugin test = plugin.getServer().getPluginManager()
                .getPlugin(entry.getName());
        if (test != null)
        {
            final StringBuffer sb = new StringBuffer(entry.getName());
            if (purge)
            {
                sb.insert(0, '$');
            }
            
            List<String> deleteList = plugin.getConfig().getStringList(
                    "delete-queue");
            
            if (deleteList == null)
            {
                deleteList = Lists.newArrayList();
            }
            
            deleteList.add(sb.toString());
            plugin.getConfig().set("delete-queue", deleteList);
            
            return true;
        }
        
        return false;
    }
    
    public List<PluginEntry> search(Pattern pattern)
    {
        final List<PluginEntry> results = new ArrayList<PluginEntry>();
        
        for (final Repository repository : repositories)
        {
            results.addAll(repository.search(pattern));
        }
        
        return results;
    }
    
    // Getter/Setter
    
    public File getTempDir()
    {
        return tempDir;
    }
    
    public void setTempDir(File tempDir)
    {
        this.tempDir = tempDir;
        
        if (!tempDir.exists())
        {
            tempDir.mkdirs();
        }
    }
    
    public File getPluginsDir()
    {
        return pluginsDir;
    }
    
    public void setPluginsDir(File pluginsDir)
    {
        this.pluginsDir = pluginsDir;
    }
    
    public File getInstallDir()
    {
        return installDir;
    }
    
    public void setInstallDir(File installDir)
    {
        this.installDir = installDir;
    }
    
    public BukkitUpdater getPlugin()
    {
        return plugin;
    }
    
    public ConfigurationSection getConfig()
    {
        return config;
    }
    
    public List<Repository> getRepositories()
    {
        return repositories;
    }
    
    public void addRepository(String url)
    {
        final Repository repository = new Repository();
        repository.setUrl(url);
        
        addRepository(repository);
    }
    
    public void addRepository(Repository repository)
    {
        if (!repositories.contains(repository))
        {
            repositories.add(repository);
        }
    }
    
    public void load()
    {
        try
        {
            for (final Repository repository : repositories)
            {
                repository.load();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
