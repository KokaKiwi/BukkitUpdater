package com.kokakiwi.bukkit.plugins.bukkitupdater.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.kokakiwi.bukkit.plugins.bukkitupdater.BukkitUpdater;

public class Libraries
{
    private final static String[] libraries = { "http://search.maven.org/remotecontent?filepath=org/jdom/jdom/1.1/jdom-1.1.jar" };
    
    public static void loadLibraries()
    {
        loadLibraries(false);
    }
    
    public static void loadLibraries(boolean overwrite)
    {
        final File dest = new File("lib/");
        if (!dest.exists())
        {
            dest.mkdirs();
        }
        
        final List<URL> libUrls = new ArrayList<URL>();
        
        for (final String lib : libraries)
        {
            try
            {
                final URL url = new URL(lib);
                final File libFile = new File(dest, url.getFile().substring(
                        url.getFile().lastIndexOf('/') + 1));
                if (!libFile.exists() || overwrite)
                {
                    loadLibrary(url, libFile);
                    libUrls.add(libFile.toURI().toURL());
                }
            }
            catch (final MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        if (libUrls.size() > 0)
        {
            final URL[] urls = libUrls.toArray(new URL[] {});
            @SuppressWarnings("unused")
            final URLClassLoader classLoader = new URLClassLoader(urls,
                    BukkitUpdater.class.getClassLoader());
        }
    }
    
    private static void loadLibrary(URL url, File dest) throws IOException
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
