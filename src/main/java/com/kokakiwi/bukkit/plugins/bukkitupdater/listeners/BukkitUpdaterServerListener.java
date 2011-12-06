package com.kokakiwi.bukkit.plugins.bukkitupdater.listeners;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class BukkitUpdaterServerListener extends ServerListener
{
    private final BukkitUpdater plugin;
    
    public BukkitUpdaterServerListener(BukkitUpdater plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public void onPluginEnable(PluginEnableEvent event)
    {
        plugin.getPermissions().reload();
    }
}
