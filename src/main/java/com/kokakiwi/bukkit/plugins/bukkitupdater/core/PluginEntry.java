package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.util.ArrayList;
import java.util.List;
import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Version;

public class PluginEntry
{
    private String            id               = null;
    private String            name             = null;
    private Version           version          = null;
    private String            author           = null;
    private String            description      = null;
    private String            fileUrl          = null;
    private String            remoteEntryUrl   = null;
    private List<Dependency>  dependencies     = null;
    private int               minBukkitBuild   = 0;
    
    public PluginEntry()
    {
        
    }
    
    public PluginEntry(String id, String name, Version version, String author,
            String description, String fileUrl, String remoteEntryUrl,
            List<Dependency> dependencies, int minBukkitBuild)
    {
        this.id = id;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
        this.fileUrl = fileUrl;
        this.remoteEntryUrl = remoteEntryUrl;
        this.dependencies = dependencies;
        this.minBukkitBuild = minBukkitBuild;
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
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PluginEntry other = (PluginEntry) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
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
            StringBuilder builder = new StringBuilder();
            builder.append("Dependency [id=");
            builder.append(id);
            builder.append(", minimumVersion=");
            builder.append(minimumVersion);
            builder.append("]");
            return builder.toString();
        }
    }
}
