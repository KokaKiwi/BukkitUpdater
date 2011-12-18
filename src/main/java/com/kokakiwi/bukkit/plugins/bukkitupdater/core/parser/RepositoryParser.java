package com.kokakiwi.bukkit.plugins.bukkitupdater.core.parser;

import java.util.Iterator;

import org.jdom.Element;

import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.Repository;

public class RepositoryParser
{
    @SuppressWarnings("unchecked")
    public static void parse(Repository repository, Element root)
            throws Exception
    {
        // Load plugins
        final Element plugins = root.getChild("plugins");
        if (plugins != null)
        {
            final Iterator<Element> iterator = plugins.getChildren().iterator();
            while (iterator.hasNext())
            {
                final Element plugin = iterator.next();
                final PluginEntry entry = new PluginEntry();
                PluginParser.parse(entry, plugin);
                repository.addPlugin(entry);
            }
        }
    }
}
