package com.kokakiwi.bukkit.plugins.bukkitupdater.commands;

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class UpdaterCommands
{
    private final BukkitUpdater plugin;
    
    public UpdaterCommands(BukkitUpdater plugin)
    {
        this.plugin = plugin;
    }
    
    @Command(aliases = { "search" }, desc = "Search a plugin in repositories by his id or name.", flags = "N:C", min = 1)
    @CommandPermissions("bukkitupdater.search")
    public void search(CommandContext args, CommandSender sender)
    {
        final StringBuffer sb = new StringBuffer(args.getString(0));
        if (sb.charAt(0) == '!')
        {
            sb.setCharAt(0, '^');
        }
        
        if (sb.charAt(sb.length() - 1) == '!')
        {
            sb.setCharAt(sb.length() - 1, '$');
        }
        
        int page = 0;
        int num = 8;
        
        if (args.argsLength() == 2)
        {
            page = args.getInteger(1) - 1;
        }
        if (args.hasFlag('N'))
        {
            num = Integer.parseInt(args.getFlag('N'));
        }
        
        final Pattern pattern = Pattern.compile(sb.toString());
        final List<PluginEntry> results = plugin.getUpdater().search(pattern);
        if (!results.isEmpty())
        {
            
            final int start = page * num;
            final int end = (page + 1) * num;
            final int pageMax = (results.size() - results.size() % num) / num
                    + 1;
            
            if (start < results.size() || page > pageMax)
            {
                sender.sendMessage(ChatColor.GOLD + "Found " + ChatColor.AQUA
                        + results.size() + ChatColor.GOLD + " results :");
                
                for (int i = start; i < end; i++)
                {
                    if (i < results.size())
                    {
                        final PluginEntry entry = results.get(i);
                        
                        if (args.hasFlag('C'))
                        {
                            final List<String> lines = entry
                                    .completePresentation(1);
                            for (final String line : lines)
                            {
                                sender.sendMessage(line);
                            }
                            
                            if (i < end - 1)
                            {
                                sender.sendMessage(ChatColor.GOLD + "------");
                            }
                        }
                        else
                        {
                            sender.sendMessage(ChatColor.GREEN + "- "
                                    + entry.smartPresentation());
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                
                sender.sendMessage(ChatColor.GOLD + "=========== Page "
                        + ChatColor.AQUA + (page + 1) + "/" + pageMax
                        + ChatColor.GOLD + " ============");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "There is no results! :(");
        }
    }
    
    @Command(aliases = { "info" }, desc = "Get informations about a plugin. You must specify his complete ID.", min = 1)
    @CommandPermissions("bukkitupdater.info")
    public void info(CommandContext args, CommandSender sender)
    {
        final List<PluginEntry> entries = plugin.getUpdater().search(
                Pattern.compile("^" + args.getString(0) + "$"));
        
        if (!entries.isEmpty())
        {
            sender.sendMessage(ChatColor.GOLD
                    + "==============================");
            
            final PluginEntry entry = entries.get(0);
            for (final String line : entry.completePresentation())
            {
                sender.sendMessage(line);
            }
            
            sender.sendMessage(ChatColor.GOLD
                    + "==============================");
        }
        else
        {
            sender.sendMessage(ChatColor.RED
                    + "Not found any plugin with this ID!");
        }
    }
    
    @Command(aliases = { "update" }, desc = "Update all plugins loaded in Server.")
    @CommandPermissions("bukkitupdater.update")
    public void update(CommandContext args, CommandSender sender)
    {
        sender.sendMessage(ChatColor.GOLD + "Updating...");
        final List<PluginEntry> updated = plugin.getUpdater().update();
        if (!updated.isEmpty())
        {
            sender.sendMessage(ChatColor.GOLD + "Done. " + updated.size()
                    + " plugins updated:");
            for (final PluginEntry entry : updated)
            {
                sender.sendMessage(ChatColor.GREEN + "- "
                        + entry.smartPresentation());
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "There's no update available.");
        }
    }
    
    @Command(aliases = { "install" }, desc = "Install a plugin specified by his ID.", min = 1)
    @CommandPermissions("bukkitupdater.install")
    public void install(CommandContext args, CommandSender sender)
            throws Exception
    {
        final List<PluginEntry> search = plugin.getUpdater().search(
                Pattern.compile("^" + args.getString(0) + "$"));
        if (!search.isEmpty())
        {
            final PluginEntry entry = search.get(0);
            sender.sendMessage(ChatColor.GOLD + "Installing '"
                    + entry.getName() + "'");
            if (plugin.getUpdater().install(entry))
            {
                sender.sendMessage(ChatColor.GREEN
                        + "Plugin successfuly installed!");
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Error during installing '"
                        + entry.getName() + " v" + entry.getVersion() + "'");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED
                    + "There's no plugin with this ID!");
        }
    }
    
    @Command(aliases = { "delete" }, desc = "Delete a plugin specified by his ID. Add 'p' flag to purge too.", min = 1, flags = "p")
    @CommandPermissions("bukkitupdater.delete")
    public void delete(CommandContext args, CommandSender sender)
    {
        final List<PluginEntry> search = plugin.getUpdater().search(
                Pattern.compile("^" + args.getString(0) + "$"));
        if (!search.isEmpty())
        {
            final PluginEntry entry = search.get(0);
            if (plugin.getUpdater().delete(entry, args.hasFlag('p')))
            {
                sender.sendMessage(ChatColor.GREEN
                        + "Plugin successfuly deleted!");
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Error during delete '"
                        + entry.getName() + "'");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED
                    + "There's no plugin with this ID!");
        }
    }
    
    @Command(aliases = { "purge" }, desc = "Purge a plugin specified by his ID. It's like 'delete -p' command.", min = 1)
    @CommandPermissions("bukkitupdater.purge")
    public void purge(CommandContext args, CommandSender sender)
    {
        final List<PluginEntry> search = plugin.getUpdater().search(
                Pattern.compile("^" + args.getString(0) + "$"));
        if (!search.isEmpty())
        {
            final PluginEntry entry = search.get(0);
            if (plugin.getUpdater().delete(entry, true))
            {
                sender.sendMessage(ChatColor.GREEN
                        + "Plugin successfuly purged!");
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Error during purge '"
                        + entry.getName() + "'");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED
                    + "There's no plugin with this ID!");
        }
    }
}
