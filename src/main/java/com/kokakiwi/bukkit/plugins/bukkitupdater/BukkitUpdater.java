package com.kokakiwi.bukkit.plugins.bukkitupdater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.BukkitUpdaterCommandsManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.CommandsManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.PluginCommands;
import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.UpdaterCommands;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.Updater;
import com.kokakiwi.bukkit.plugins.bukkitupdater.listeners.BukkitUpdaterListenersManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl.BukkitBridge;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl.GroupManagerBridge;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl.PermissionsExBridge;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl.PermissionsPluginBridge;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Libraries;

public class BukkitUpdater extends JavaPlugin
{
    public final static Logger            LOGGER   = Logger.getLogger("BukkitUpdater");
    
    static
    {
        LOGGER.setFilter(new Filter() {
            
            public boolean isLoggable(LogRecord record)
            {
                record.setMessage("[KiwiCraft] " + record.getMessage());
                return true;
            }
        });
    }
    
    private BukkitUpdaterListenersManager listenersManager;
    private PermissionsManager            permissions;
    private BukkitUpdaterCommandsManager  commands = null;
    
    private Updater                       updater;
    
    public void onEnable()
    {
        registerListeners();
        registerPermissions();
        registerCommands();
        
        loadUpdater();
        
        LOGGER.info("BukkitUpdater v" + getDescription().getVersion()
                + " enabled.");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onLoad()
    {
        loadConfiguration();
        
        final List<String> deleteList = getConfig().getStringList(
                "delete-queue");
        if (!deleteList.isEmpty())
        {
            for (final String pluginName : deleteList)
            {
                String name = pluginName;
                boolean purge = false;
                if (name.charAt(0) == '$')
                {
                    name = name.substring(1);
                    purge = true;
                }
                
                final Plugin plugin = getServer().getPluginManager().getPlugin(
                        name);
                if (plugin != null)
                {
                    getServer().getPluginManager().disablePlugin(plugin);
                    if (purge)
                    {
                        deleteFolder(plugin.getDataFolder());
                    }
                }
            }
        }
        getConfig().set("delete-queue", null);
        
        LOGGER.info("Loading libraries...");
        loadLibraries();
    }
    
    private void deleteFolder(File fold)
    {
        for (final File file : fold.listFiles())
        {
            if (file.isDirectory())
            {
                deleteFolder(file);
            }
            
            file.delete();
        }
        fold.delete();
    }
    
    private void registerListeners()
    {
        listenersManager = new BukkitUpdaterListenersManager(this);
        listenersManager.registerListeners();
    }
    
    private void registerPermissions()
    {
        permissions = new PermissionsManager(getServer());
        
        try
        {
            permissions.register(BukkitBridge.class);
            permissions.register(PermissionsExBridge.class);
            permissions.register(GroupManagerBridge.class);
            permissions.register(PermissionsPluginBridge.class);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        
        permissions.reload();
    }
    
    private void registerCommands()
    {
        final Plugin test = getServer().getPluginManager().getPlugin(
                "WorldEdit");
        
        if (test != null)
        {
            commands = new BukkitUpdaterCommandsManager(this);
            
            commands.register(PluginCommands.class);
            commands.register(UpdaterCommands.class);
        }
        else
        {
            LOGGER.severe("ERROR. Plugin couldn't found WorldEdit. You MUST have WorldEdit to run BukkitUpdater properly!");
        }
    }
    
    private void loadConfiguration()
    {
        try
        {
            getConfig().load(getResource("config.yml"));
            
            final File configFile = new File(getDataFolder(), "config.yml");
            if (configFile.exists())
            {
                getConfig().load(new File(getDataFolder(), "config.yml"));
            }
            else
            {
                getDataFolder().mkdirs();
                new File(getDataFolder(), "config.yml").createNewFile();
                getConfig().save(new File(getDataFolder(), "config.yml"));
            }
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        catch (final InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void loadLibraries()
    {
        Libraries.loadLibraries();
    }
    
    @SuppressWarnings("unchecked")
    private void loadUpdater()
    {
        ConfigurationSection section = getConfig().getConfigurationSection(
                "updater");
        
        if (section == null)
        {
            section = getConfig().createSection("updater");
        }
        
        updater = new Updater(this, section);
        updater.setTempDir(new File("updater-tmp"));
        updater.setInstallDir(getServer().getUpdateFolderFile());
        updater.setPluginsDir(new File("plugins"));
        
        final List<String> repositories = getConfig().getStringList(
                "repositories");
        for (final String repo : repositories)
        {
            updater.addRepository(repo);
        }
        
        getServer().getScheduler().scheduleAsyncDelayedTask(this,
                new Runnable() {
                    
                    public void run()
                    {
                        LOGGER.info("Initializing Updater...");
                        updater.initialize();
                        LOGGER.info("Updater initalized! Enjoy :)");
                    }
                });
    }
    
    public void onDisable()
    {
        LOGGER.info("BukkitUpdater v" + getDescription().getVersion()
                + " disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args)
    {
        return CommandsManager
                .onCommand(commands, sender, command, label, args);
    }
    
    public BukkitUpdaterListenersManager getListenersManager()
    {
        return listenersManager;
    }
    
    public PermissionsManager getPermissions()
    {
        return permissions;
    }
    
    public BukkitUpdaterCommandsManager getCommands()
    {
        return commands;
    }
    
    public Updater getUpdater()
    {
        return updater;
    }
    
}
