package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import com.google.common.collect.Maps;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Version;

public class PluginEntry
{
    private String           id             = null;
    private String           name           = null;
    private Version          version        = null;
    private String           author         = null;
    private String           description    = null;
    private String           fileUrl        = null;
    private String           remoteEntryUrl = null;
    private List<Dependency> dependencies   = null;
    private int              minBukkitBuild = 0;
    
    public PluginEntry()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Version getVersion()
    {
        return version;
    }
    
    public void setVersion(Version version)
    {
        this.version = version;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getFileUrl()
    {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }
    
    public String getRemoteEntryUrl()
    {
        return remoteEntryUrl;
    }
    
    public void setRemoteEntryUrl(String remoteEntryUrl)
    {
        this.remoteEntryUrl = remoteEntryUrl;
    }
    
    public List<Dependency> getDependencies()
    {
        if (dependencies == null)
        {
            dependencies = new ArrayList<PluginEntry.Dependency>();
        }
        
        return dependencies;
    }
    
    public void setDependencies(List<Dependency> dependencies)
    {
        this.dependencies = dependencies;
    }
    
    public int getMinBukkitBuild()
    {
        return minBukkitBuild;
    }
    
    public void setMinBukkitBuild(int minBukkitBuild)
    {
        this.minBukkitBuild = minBukkitBuild;
    }
    
    public String smartPresentation()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append(name);
        buf.append(" v");
        buf.append(version);
        buf.append(" by ");
        buf.append(author);
        buf.append(" [");
        buf.append(id);
        buf.append(']');
        
        return buf.toString();
    }
    
    public List<String> completePresentation()
    {
        return completePresentation(0);
    }
    
    public List<String> completePresentation(int indent)
    {
        final Map<String, Object> map = Maps.newLinkedHashMap();
        
        map.put("ID", id);
        map.put("Name", name);
        map.put("Version", version);
        map.put("Author", author);
        if (description.length() > 0)
        {
            map.put("Description", description);
        }
        if (minBukkitBuild > 0)
        {
            map.put("Min Bukkit Build:", "#" + String.valueOf(minBukkitBuild));
        }
        
        final List<String> pres = Presentation.present(map, indent);
        
        return pres;
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
        final PluginEntry other = (PluginEntry) obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("PluginEntry [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", version=");
        builder.append(version);
        builder.append(", author=");
        builder.append(author);
        builder.append(", description=");
        builder.append(description);
        builder.append(", fileUrl=");
        builder.append(fileUrl);
        builder.append(", remoteEntryUrl=");
        builder.append(remoteEntryUrl);
        builder.append(", dependencies=");
        builder.append(dependencies);
        builder.append(", minBukkitBuild=");
        builder.append(minBukkitBuild);
        builder.append("]");
        return builder.toString();
    }
    
    public static class Dependency
    {
        private String  id;
        private Version minimumVersion;
        
        public Dependency()
        {
            
        }
        
        public Dependency(String id, Version minimumVersion)
        {
            this.id = id;
            this.minimumVersion = minimumVersion;
        }
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public Version getMinimumVersion()
        {
            return minimumVersion;
        }
        
        public void setMinimumVersion(Version minimumVersion)
        {
            this.minimumVersion = minimumVersion;
        }
        
        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();
            
            builder.append(id);
            builder.append(" (");
            builder.append(minimumVersion);
            builder.append(']');
            
            return builder.toString();
        }
    }
    
    public static class Presentation
    {
        public static List<String> present(Map<String, Object> map)
        {
            return present(map, 0);
        }
        
        @SuppressWarnings("unchecked")
        public static List<String> present(Map<String, Object> map, int indent)
        {
            final List<String> list = new ArrayList<String>();
            
            for (final Entry<String, Object> entry : map.entrySet())
            {
                final StringBuffer sb = new StringBuffer();
                
                for (int i = 0; i < indent; i++)
                {
                    sb.append(' ');
                }
                
                sb.append(ChatColor.GREEN);
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(ChatColor.AQUA);
                
                if (entry.getValue() instanceof List)
                {
                    list.add(sb.toString());
                    final List<Object> sub = (List<Object>) entry.getValue();
                    for (final Object o : sub)
                    {
                        final StringBuffer subb = new StringBuffer();
                        for (int i = 0; i <= indent; i++)
                        {
                            subb.append(' ');
                        }
                        subb.append(ChatColor.AQUA);
                        subb.append("- ");
                        subb.append(o.toString());
                        list.add(subb.toString());
                    }
                }
                else if (entry.getValue() instanceof Map)
                {
                    list.add(sb.toString());
                    list.addAll(present((Map<String, Object>) entry.getValue(),
                            indent + 1));
                }
                else
                {
                    sb.append(entry.getValue().toString());
                    list.add(sb.toString());
                }
            }
            
            return list;
        }
    }
}
