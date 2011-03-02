package org.kokakiwi.bukkitupdater.utils;

import java.util.regex.Pattern;

public class VersionComparator {
	public static String compare(String v1, String v2) {
        String s1 = normalisedVersion(v1);
        String s2 = normalisedVersion(v2);
        int cmp = s1.compareTo(s2);
        if(cmp < 0)
        	return "<";
        else if(cmp > 0)
        	return ">";
        else
        	return "==";
        
    }

    public static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    public static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}
