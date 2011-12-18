package com.kokakiwi.bukkit.plugins.bukkitupdater.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.Injector;

public class BukkitUpdaterCommandsManager extends
        CommandsManager<CommandSender>
{
    private final BukkitUpdater plugin;
    
    public BukkitUpdaterCommandsManager(BukkitUpdater plugin)
    {
        this.plugin = plugin;
        injector = new MyInjector(plugin);
    }
    
    @Override
    public boolean hasPermission(CommandSender sender, String perm)
    {
        boolean has = true;
        
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;
            has = plugin.getPermissions().getBridge().has(player, perm);
        }
        
        return has;
    }
    
    public static class MyInjector implements Injector
    {
        private final BukkitUpdater plugin;
        
        public MyInjector(BukkitUpdater plugin)
        {
            this.plugin = plugin;
        }
        
        public Object getInstance(Class<?> cls)
                throws InvocationTargetException, IllegalAccessException,
                InstantiationException
        {
            Constructor<?> constructor = null;
            
            try
            {
                constructor = cls.getConstructor(BukkitUpdater.class);
            }
            catch (final NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (final SecurityException e)
            {
                e.printStackTrace();
            }
            
            return constructor == null ? null : constructor.newInstance(plugin);
        }
        
    }
    
}
