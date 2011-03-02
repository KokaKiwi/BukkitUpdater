package org.kokakiwi.bukkitupdater.updater;

public class BPlugin {
	public final String id;
	public final String name;
	public final String version;
	public final String author;
	public final String jarUrl;
	public String url = "";
	
	public BPlugin(String id, String name, String version, String author, String jarUrl)
	{
		this.id = id;
		this.name = name;
		this.version = version;
		this.author = author;
		this.jarUrl = jarUrl;
	}
}
