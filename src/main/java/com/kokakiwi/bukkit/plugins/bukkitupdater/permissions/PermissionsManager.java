package com.kokakiwi.bukkit.plugins.bukkitupdater.permissions;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.bukkit.Server;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class PermissionsManager
{
    private final Server                       server;
    
    private PermissionsBridge                  bridge = null;
    private final SortedSet<PermissionsBridge> bridges;
    
    public PermissionsManager(Server server)
    {
        this.server = server;
        bridges = new TreeSet<PermissionsBridge>(
                new PermissionsBridgeComparator());
    }
    
    public Server getServer()
    {
        return server;
    }
    
    public PermissionsBridge getBridge()
    {
        return bridge;
    }
    
    public void setBridge(PermissionsBridge bridge)
    {
        this.bridge = bridge;
    }
    
    public SortedSet<PermissionsBridge> getBridges()
    {
        return bridges;
    }
    
    public <T extends PermissionsBridge> void register(Class<T> clazz)
            throws Exception
    {
        Constructor<T> constructor = clazz.getConstructor();
        T tmp = constructor.newInstance();
        
        register(tmp);
    }
    
    public void reload()
    {
        for (PermissionsBridge b : bridges)
        {
            if (b.check(server))
            {
                bridge = b;
                BukkitUpdater.LOGGER.info("Permissions Bridge '" + b.getName() + "' binded.");
                break;
            }
        }
    }
    
    public <T extends PermissionsBridge> void register(T e)
    {
        if (!bridges.contains(e))
        {
            bridges.add(e);
        }
    }
    
    public static class PermissionsBridgeComparator implements
            Comparator<PermissionsBridge>
    {
        
        public int compare(PermissionsBridge i, PermissionsBridge j)
        {
            return (i.getPriority() - j.getPriority());
        }
        
    }
}
