package com.kokakiwi.bukkit.plugins.bukkitupdater.utils;

public class Version implements Comparable<Version>
{
    private final long[] nums;
    
    public Version(long... is)
    {
        nums = is;
    }
    
    public long[] getVersionNumbers()
    {
        return nums;
    }
    
    public static Version parseString(String version)
    {
        if (version.lastIndexOf('-') > 0)
        {
            version = version.substring(0, version.lastIndexOf('-'));
        }
        
        version = version.replaceAll("[a-zA-Z ]*", "");
        
        final String[] splitted = version.split("\\.");
        final long[] nums = new long[splitted.length];
        for (int i = 0; i < splitted.length; i++)
        {
            String part = splitted[i];
            
            if (part.length() == 0)
            {
                part = "0";
            }
            
            nums[i] = Long.parseLong(part);
        }
        
        return new Version(nums);
    }
    
    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < nums.length; i++)
        {
            sb.append(nums[i]);
            if (i < nums.length - 1)
            {
                sb.append('.');
            }
        }
        
        return sb.toString();
    }
    
    public int compareTo(Version arg)
    {
        final int minNums = Math.min(nums.length,
                arg.getVersionNumbers().length);
        
        int diff = 0;
        for (int i = 0; i < minNums; i++)
        {
            if (nums[i] > arg.getVersionNumbers()[i])
            {
                diff++;
                break;
            }
            else if (nums[i] < arg.getVersionNumbers()[i])
            {
                diff--;
                break;
            }
            else
            {
                continue;
            }
        }
        
        if (diff == 0)
        {
            if (nums.length > arg.getVersionNumbers().length)
            {
                diff++;
            }
            else if (nums.length < arg.getVersionNumbers().length)
            {
                diff--;
            }
        }
        
        return diff;
    }
    
}
