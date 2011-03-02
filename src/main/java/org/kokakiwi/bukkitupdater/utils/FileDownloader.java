package org.kokakiwi.bukkitupdater.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.kokakiwi.bukkitupdater.BukkitUpdater;

public class FileDownloader {
	
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.FileDownloader");
	private final BukkitUpdater plugin;
	
	public FileDownloader(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
	}
	
	public boolean download(String remoteUrl, File file)
	{
		try {
			URL url = new URL(remoteUrl);
			
			if(file.exists())
				file.delete();
			
			InputStream in = url.openStream();
			OutputStream out = new FileOutputStream(file);
			
			saveTo(in, out);
			
			in.close();
			out.close();
			
			return true;
		} catch (MalformedURLException e) {
			logger.warning("BukkitUpdater : Error during downloading " + remoteUrl);
			e.printStackTrace();
		} catch (IOException e) {
			logger.warning("BukkitUpdater : Error during downloading " + remoteUrl);
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void saveTo(InputStream inputStream, OutputStream outputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
	}
}
