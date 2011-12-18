package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsBridge;

public class PermissionsExBridge implements PermissionsBridge
{
    private PermissionsEx plugin;
    
    public boolean has(Player player, String permission)
    {
        return plugin.has(player, permission);
    }
    
    public boolean check(Server server)
    {
        final Plugin test = server.getPluginManager()
                .getPlugin("PermissionsEx");
        
        if (test != null)
        {
            plugin = (PermissionsEx) test;
            return true;
        }
        
        return false;
    }
    
    public int getPriority()
    {
        return 30;
    }
    
    public String getName()
    {
        return "PermissionsEx v" + plugin.getDescription().getVersion();
    }
    
}
