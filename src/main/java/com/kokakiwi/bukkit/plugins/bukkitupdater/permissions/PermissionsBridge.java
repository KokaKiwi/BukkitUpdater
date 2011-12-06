package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public interface PermissionsBridge
{
    public String getName();
    
    public boolean has(Player player, String permission);
    
    public boolean check(Server server);
    
    public int getPriority();
}
