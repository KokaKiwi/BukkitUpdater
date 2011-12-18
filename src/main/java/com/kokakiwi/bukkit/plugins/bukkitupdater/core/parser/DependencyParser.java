package com.kokakiwi.bukkit.plugins.bukkitupdater.core.parser;

import org.jdom.Element;

import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry.Dependency;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Version;

public class DependencyParser
{
    public static Dependency parse(Element root)
    {
        final Dependency dependency = new Dependency();
        
        dependency.setId(root.getAttributeValue("id"));
        dependency.setMinimumVersion(Version.parseString(root
                .getAttributeValue("min-version")));
        
        return dependency;
    }
}
