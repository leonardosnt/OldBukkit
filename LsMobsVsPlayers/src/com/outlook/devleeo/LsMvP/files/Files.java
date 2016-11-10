package com.outlook.devleeo.LsMvP.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Files {
	
	private static Files instance = new Files();
	private FileConfiguration data;
	private File dfile;
	
	public static Files getInstance() {
		return instance;
	}
	
	
	public void setupFiles(Plugin p) {
		
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		
		dfile = new File(p.getDataFolder(), "data.yml");
		
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("§cErro ao criar o arquivo data.yml");
			}
		}
		
		data = YamlConfiguration.loadConfiguration(dfile);
	}
	
	public FileConfiguration getData() {
		return data;
	}
	
	public void saveData() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cErro ao salvar o arquivo data.yml");
		}
	}
}