package com.kokakiwi.bukkit.plugins.bukkitupdater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.BukkitUpdaterCommandsManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.commands.PluginCommands;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.Updater;
import com.kokakiwi.bukkit.plugins.bukkitupdater.listeners.BukkitUpdaterListenersManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsManager;
import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl.*;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Libraries;
import com.sk89q.minecraft.util.commands.CommandException;

public class BukkitUpdater extends JavaPlugin
{
    public final static Logger            LOGGER = Logger.getLogger("BukkitUpdater");
    
    private BukkitUpdaterListenersManager listenersManager;
    private PermissionsManager            permissions;
    private BukkitUpdaterCommandsManager  commands;
    
    private Updater                       updater;
    
    public void onEnable()
    {
        registerListeners();
        registerPermissions();
        registerCommands();
        
        loadConfiguration();
        loadLibraries();
        
        loadUpdater();
        
        LOGGER.info("BukkitUpdater v" + getDescription().getVersion()
                + " enabled.");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        permissions.reload();
    }
    
    private void registerCommands()
    {
        commands = new BukkitUpdaterCommandsManager(this);
        
        commands.register(PluginCommands.class);
    }
    
    private void loadConfiguration()
    {
        try
        {
            getConfig().load(getResource("config.yml"));
            
            File configFile = new File(getDataFolder(), "config.yml");
            if (configFile.exists())
            {
                getConfig().load(new File(getDataFolder(), "config.yml"));
            }
            else
            {
                new File(getDataFolder(), "config.yml").mkdirs();
                new File(getDataFolder(), "config.yml").createNewFile();
                getConfig().save(new File(getDataFolder(), "config.yml"));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void loadLibraries()
    {
        Libraries.loadLibraries();
    }
    
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
        updater.initialize();
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
        try
        {
            String commandName = command.getName();
            String[] commandArgs = args;
            
            if (command.getName().equalsIgnoreCase("updater"))
            {
                if (args.length == 0)
                {
                    throw new CommandException("No args!");
                }
                
                commandName = args[0];
                System.arraycopy(args, 1, commandArgs, 0, args.length - 1);
            }
            
            commands.execute(commandName, commandArgs, sender, sender);
        }
        catch (CommandException e)
        {
            sender.sendMessage(ChatColor.RED + "Error.");
            e.printStackTrace();
        }
        
        return true;
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
