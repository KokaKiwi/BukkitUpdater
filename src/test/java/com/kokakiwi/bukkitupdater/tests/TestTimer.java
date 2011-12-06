package com.kokakiwi.bukkitupdater.tests;

import java.util.concurrent.TimeUnit;

public class TestTimer
{
    private static long last = 0;
    
    public static void update()
    {
        last = System.currentTimeMillis();
    }
    
    public static long diff()
    {
        long current = System.currentTimeMillis();
        return current - last;
    }
    
    public static double getSeconds(long diff)
    {
        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }
    
    public static void check()
    {
        long diff = diff();
        double seconds = getSeconds(diff);
        
        System.out.println("Taken: " + seconds + "s (" + diff + "ms)");
    }
}
