package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.impl;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.kokakiwi.bukkit.plugins.bukkitupdater.permissions.PermissionsBridge;

public class BukkitBridge implements PermissionsBridge
{
    public boolean has(Player player, String permission)
    {
        return player.hasPermission(permission);
    }
    
    public boolean check(Server server)
    {
        return true;
    }
    
    public int getPriority()
    {
        return 1000;
    }

    public String getName()
    {
        return "Bukkit Perms";
    }
    
}
