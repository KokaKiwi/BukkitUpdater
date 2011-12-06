package com.kokakiwi.bukkit.plugins.bukkitupdater.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils
{
    public static String hashMd5(String str)
    {
        return bytesToHexString(hashMd5(str.getBytes(Charset.forName("UTF-8"))));
    }
    
    public static byte[] hashMd5(byte[] bytes)
    {
        try
        {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes, 0, bytes.length);
            final byte[] first = digest.digest();
            return digest.digest(first);
        }
        catch (final NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public static String bytesToHexString(byte[] bytes)
    {
        final StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (final byte b : bytes)
        {
            final String s = Integer.toString(0xFF & b, 16);
            if (s.length() < 2)
            {
                buf.append('0');
            }
            buf.append(s);
        }
        return buf.toString();
    }
}
