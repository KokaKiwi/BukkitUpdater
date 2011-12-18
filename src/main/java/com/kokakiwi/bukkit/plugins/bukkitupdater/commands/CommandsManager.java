package com.kokakiwi.bukkit.plugins.bukkitupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandUsageException;

public class CommandsManager
{
    public static boolean onCommand(BukkitUpdaterCommandsManager commands,
            CommandSender sender, Command command, String label, String[] args)
    {
        if (commands != null)
        {
            try
            {
                String commandName = command.getName();
                String[] commandArgs = args;
                
                if (command.getName().equalsIgnoreCase("updater"))
                {
                    if (args.length == 0)
                    {
                        sender.sendMessage("Too few arguments.");
                    }
                    
                    commandArgs = new String[args.length - 1];
                    
                    commandName = args[0];
                    System.arraycopy(args, 1, commandArgs, 0, args.length - 1);
                }
                
                commands.execute(commandName, commandArgs, sender, sender);
            }
            catch (final CommandUsageException e)
            {
                sender.sendMessage(ChatColor.RED + e.getMessage());
                final StringBuffer sb = new StringBuffer(e.getUsage());
                if (command.getName().equalsIgnoreCase("updater"))
                {
                    sb.insert(1, new StringBuffer().append(command.getName())
                            .append(' '));
                }
                sender.sendMessage(ChatColor.RED + "Correct usage: "
                        + sb.toString());
            }
            catch (final CommandException e)
            {
                e.printStackTrace();
            }
        }
        
        return true;
    }
}
