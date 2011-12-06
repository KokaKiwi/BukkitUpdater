package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsBridge;

public class GroupManagerBridge implements PermissionsBridge
{
    private GroupManager plugin;
    
    @SuppressWarnings("deprecation")
    public boolean has(Player player, String permission)
    {
        return plugin.getPermissionHandler().has(player, permission);
    }
    
    public boolean check(Server server)
    {
        Plugin test = server.getPluginManager().getPlugin("");
        
        if (test != null)
        {
            plugin = (GroupManager) test;
            return true;
        }
        
        return false;
    }
    
    public int getPriority()
    {
        return 50;
    }

    public String getName()
    {
        return "GroupManager v" + plugin.getDescription().getVersion();
    }
    
}
