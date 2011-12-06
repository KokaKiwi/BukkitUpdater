package com.kokakiwi.bukkit.plugins.bukkitupdater.listeners;

import org.bukkit.event.Event;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class BukkitUpdaterListenersManager
{
    private final BukkitUpdater               plugin;
    
    // Listeners
    private final BukkitUpdaterServerListener serverListener;
    
    public BukkitUpdaterListenersManager(BukkitUpdater plugin)
    {
        this.plugin = plugin;
        serverListener = new BukkitUpdaterServerListener(plugin);
    }
    
    public void registerListeners()
    {
        plugin.getServer()
                .getPluginManager()
                .registerEvent(Event.Type.PLUGIN_ENABLE, serverListener,
                        Event.Priority.Normal, plugin);
    }
}
