package com.kokakiwi.bukkitupdater.tests;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.After;
import org.junit.Test;

import com.kokakiwi.bukkit.plugins.bukkitupdater.core.PluginEntry;
import com.kokakiwi.bukkit.plugins.bukkitupdater.core.Updater;

public class RepositoryTest
{
    public final static String testRepository = "http://minerepo.tk/repo.xml";
    private Updater            updater;
    
    @Test
    public void test() throws Exception
    {
        TestTimer.update();
        System.out.println("Load repository...");
        YamlConfiguration config = new YamlConfiguration();
        updater = new Updater(null, config.createSection("updater"));
        updater.setTempDir(new File("temp"));
        updater.addRepository(testRepository);
        updater.initialize();
        System.out.println("Repository loaded.");
        TestTimer.check();
    }
    
    @After
    public void testSearch()
    {
        TestTimer.update();
        System.out.println("Testing search...");
        List<PluginEntry> entries = updater.search(Pattern.compile(""));
        System.out.println("Finished: " + entries.size() + " found!");
        TestTimer.check();
    }
    
}
