package com.kokakiwi.bukkit.plugins.bukkitupdater.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class PluginInstaller
{
    public static boolean install(PluginEntry entry, File dest, File tmp)
            throws Exception
    {
        final URL url = new URL(entry.getFileUrl());
        File file = new File(dest, url.getFile().substring(
                url.getFile().lastIndexOf('/') == -1 ? 0 : url.getFile()
                        .lastIndexOf('/')));
        
        downloadFile(url, file);
        
        return true;
    }
    
    private static void downloadFile(URL url, File dest) throws Exception
    {
        if (dest.exists())
        {
            dest.delete();
        }
        
        BukkitUpdater.LOGGER
                .info(" - Downloading '" + dest.getName() + "' ...");
        
        final URLConnection connection = url.openConnection();
        final OutputStream out = new FileOutputStream(dest);
        final InputStream in = connection.getInputStream();
        
        final byte[] bytes = new byte[1024];
        int size = 0;
        int readed;
        while ((readed = in.read(bytes)) > 0)
        {
            out.write(bytes, 0, readed);
            size += readed;
        }
        
        in.close();
        out.close();
        
        final double size_ko = size / 1024;
        
        BukkitUpdater.LOGGER.info(" - Downloaded  '" + dest.getName() + "' - "
                + size_ko + " Ko.");
    }
}
