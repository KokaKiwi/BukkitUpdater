package org.kokakiwi.bukkitupdater.updater;

import java.util.List;

import org.jdom.Element;

public class BPlugin {
	public final String id;
	public final String name;
	public final String version;
	public final String author;
	public final String fileType;
	public final String fileUrl;
	
	public String url = "";
	public Element archive = null;
	public List dependencies = null;
	public String bukkitBuild = null;
	
	public BPlugin(String id, String name, String version, String author, String fileType, String fileUrl)
	{
		this.id = id;
		this.name = name;
		this.version = version;
		this.author = author;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
	}
}
