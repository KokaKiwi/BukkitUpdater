package org.kokakiwi.bukkitupdater.utils;

import java.io.*;
import java.util.zip.*;

public class UnZip {
	private final static int BUFFER = 2048;
	
	public static void unzip(File zipFile, File zipDest)
	{
		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(zipFile.getAbsolutePath());
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while((entry = zis.getNextEntry()) != null) {
				if(entry.isDirectory())
				{
					new File(zipDest, entry.getName()).mkdirs();
				}else {
					new File(new File(zipDest, entry.getName()).getParent()).mkdirs();
					new File(zipDest, entry.getName()).createNewFile();
					int count;
					byte data[] = new byte[BUFFER];
					// write the files to the disk
					FileOutputStream fos = new FileOutputStream(zipDest.getAbsolutePath() + "/" + entry.getName());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}
			}
			zis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
