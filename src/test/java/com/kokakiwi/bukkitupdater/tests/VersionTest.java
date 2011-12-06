package com.kokakiwi.bukkitupdater.tests;

import org.junit.Test;

import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Version;

public class VersionTest
{
    
    @Test
    public void test()
    {
        Version version = Version.parseString("0.2.0");
        
        System.out.println(version.toString());
    }
    
}
