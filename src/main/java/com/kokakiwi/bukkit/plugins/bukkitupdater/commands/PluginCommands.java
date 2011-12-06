package com.kokakiwi.bukkit.plugins.bukkitupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class PluginCommands
{
    private final BukkitUpdater plugin;
    
    public PluginCommands(BukkitUpdater plugin)
    {
        this.plugin = plugin;
    }
    
    @Command(aliases = { "version", "v" }, desc = "Get BukkitUpdater version.")
    @CommandPermissions("bukkitupdater.version")
    public void version(CommandContext context, CommandSender sender)
    {
        sender.sendMessage(ChatColor.GOLD + "BukkitUpdater v" + ChatColor.AQUA
                + plugin.getDescription().getVersion());
    }
}
