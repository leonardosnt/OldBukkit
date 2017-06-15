package com.outlook.devleeo.LsMagnata.common;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YAMLConfig
{
	
	private File file;
	private FileConfiguration configurationFile;
	private Plugin plugin;
 
	public YAMLConfig(String fileName, Plugin plugin)
	{
		File diretory = plugin.getDataFolder();
		
		if (!diretory.exists())
		{
			diretory.mkdir();
		}
		
		this.file = new File(diretory, fileName.concat(".yml"));
		this.plugin = plugin;
	}
 
	public void createFile()
	{
		if (!this.file.exists())
		{
			try {
				this.file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.configurationFile = YamlConfiguration.loadConfiguration(file);
	}
	
 
	public FileConfiguration getYmlFile()
	{
		return this.configurationFile;
	}
	
 
	public void saveDefault()
	{
		plugin.saveResource(file.getName(), true);
	}
 
	public void saveFile()
	{
		try {
			this.configurationFile.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
