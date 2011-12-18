package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsBridge;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionsPluginBridge implements PermissionsBridge
{
    private Permissions plugin;
    
    public boolean has(Player player, String permission)
    {
        return plugin.getHandler().has(player, permission);
    }
    
    public boolean check(Server server)
    {
        final Plugin test = server.getPluginManager().getPlugin("Permissions");
        if (test != null)
        {
            plugin = (Permissions) test;
            return true;
        }
        
        return false;
    }
    
    public int getPriority()
    {
        return 70;
    }
    
    public String getName()
    {
        return "Permissions v" + plugin.getDescription().getVersion();
    }
    
}
