package com.kokakiwi.bukkit.plugins.bukkitupdater.core.parser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry.Dependency;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Version;

/*
 * Plugin model (simple):
 * 
 * <plugin>
 *  <id>test-plugin</id> <!-- Mandatory -->
 *  <name>Test Plugin</name> <!-- Mandatory -->
 *  <author>A guy</author>
 *  <version>1.0.0</version> <!-- Mandatory -->
 *  <description>Some description</description>
 *  <file-url>http//some-url/forsomefile.jar</file-url> <!-- Mandatory -->
 *  <min-bukkit-build>1555</min-bukkit-build>
 *  <dependencies>
 *      <dependency id="other-plugin-id" min-version="0.5.0" />
 *  </dependencies>
 * </plugin>
 */
public class PluginParser
{
    /**
     * Parse a plugin model.
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void parse(PluginEntry entry, Element root) throws Exception
    {
        String remoteEntryUrl = root.getAttributeValue("remote");
        if (remoteEntryUrl != null)
        {
            entry.setRemoteEntryUrl(remoteEntryUrl);
            
            URL url = new URL(remoteEntryUrl);
            parseRemoteEntry(entry, url);
        }
        else
        {
            entry.setId(root.getChildText("id"));
            entry.setName(root.getChildText("name"));
            entry.setAuthor(root.getChildText("author"));
            entry.setVersion(Version.parseString(root.getChildText("version")));
            entry.setDescription(root.getChildText("description"));
            entry.setFileUrl(root.getChildText("file-url"));
            
            String minBukkitBuild = root.getChildText("min-bukkit-build");
            if(minBukkitBuild != null)
            {
                entry.setMinBukkitBuild(Integer.parseInt(minBukkitBuild));
            }
            
            List<PluginEntry.Dependency> dependencies = new ArrayList<PluginEntry.Dependency>();
            Element dependenciesNode = root.getChild("dependencies");
            if (dependenciesNode != null)
            {
                Iterator<Element> deps = dependenciesNode.getChildren()
                        .iterator();
                while (deps.hasNext())
                {
                    Element dep = deps.next();
                    Dependency dependency = DependencyParser.parse(dep);
                    dependencies.add(dependency);
                }
            }
            entry.setDependencies(dependencies);
        }
    }
    
    public static void parseRemoteEntry(PluginEntry entry, URL url)
            throws Exception
    {
        InputStream in = url.openStream();
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(in);
        
        Element root = document.getRootElement();
        parse(entry, root);
    }
}
