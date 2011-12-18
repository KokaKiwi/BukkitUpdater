package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.kokakiwi.bukkit.plugins.bukkitupdater.core.parser.RepositoryParser;

public class Repository
{
    private String                  url;
    
    private final List<PluginEntry> plugins   = new ArrayList<PluginEntry>();
    private final List<Repository>  inherited = new ArrayList<Repository>();
    
    // Initialize
    
    public void load() throws Exception
    {
        final URL repoURL = new URL(url);
        final InputStream in = repoURL.openStream();
        
        final SAXBuilder builder = new SAXBuilder();
        final Document document = builder.build(in);
        
        RepositoryParser.parse(this, document.getRootElement());
    }
    
    // Actions
    
    public List<PluginEntry> search(Pattern pattern)
    {
        final List<PluginEntry> results = new ArrayList<PluginEntry>();
        
        for (final PluginEntry entry : plugins)
        {
            if (pattern.matcher(entry.getName()).find()
                    || pattern.matcher(entry.getId()).find())
            {
                results.add(entry);
            }
        }
        
        for (final Repository repository : inherited)
        {
            results.addAll(repository.search(pattern));
        }
        
        return results;
    }
    
    // Getter / Setter
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public List<PluginEntry> getPlugins()
    {
        return plugins;
    }
    
    public void addPlugin(PluginEntry plugin)
    {
        if (!plugins.contains(plugin))
        {
            plugins.add(plugin);
        }
    }
    
    public List<Repository> getInherited()
    {
        return inherited;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Repository other = (Repository) obj;
        if (url == null)
        {
            if (other.url != null)
            {
                return false;
            }
        }
        else if (!url.equals(other.url))
        {
            return false;
        }
        return true;
    }
}
