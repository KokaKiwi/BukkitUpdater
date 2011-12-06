package com.kokakiwi.bukkitupdater.tests;
import org.junit.Test;

import com.kokakiwi.bukkit.plugins.bukkitupdater.utils.Libraries;

public class TestLibrariesDownload
{
    
    @Test
    public void test()
    {
        Libraries.loadLibraries(true);
    }
    
}
