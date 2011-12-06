package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.configuration.ConfigurationSection;
import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class Updater
{
    private final BukkitUpdater        plugin;
    private final ConfigurationSection config;
    private File                       tempDir      = null;
    
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
    
    public List<PluginEntry> search(Pattern pattern)
    {
        List<PluginEntry> results = new ArrayList<PluginEntry>();
        
        for (Repository repository : repositories)
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
        Repository repository = new Repository();
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
            for (Repository repository : repositories)
            {
                repository.load();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
