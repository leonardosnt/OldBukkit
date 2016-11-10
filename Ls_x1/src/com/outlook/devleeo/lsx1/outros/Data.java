package com.outlook.devleeo.lsx1.outros;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.lsx1.Main;

public class Data {
	
	private static Data instance = new Data();
	
	public static Data getInstance() {
		return instance;
	}
	
	FileConfiguration data;
	File dfile;
	
	FileConfiguration msgs;
	File mfile;
	
	public void setup(Plugin p) {
		
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		
		dfile = new File(p.getDataFolder(), "data.yml");
		
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe("Erro ao criar o arquivo data.yml");
			}
		}
		
		data = YamlConfiguration.loadConfiguration(dfile);
		
		//fim data, inicio msgs
		
		mfile = new File(p.getDataFolder(), "mensagens.yml");
		
		if (!mfile.exists()) {
			try {
				mfile.createNewFile();
				Main.getInstance().saveResource("mensagens.yml", true);
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe("§cErro ao criar o arquivo mensagens.yml");
			}
		}
		
		msgs = YamlConfiguration.loadConfiguration(mfile);
	}
	
	public FileConfiguration getData() {
		return data;
	}
	
	public FileConfiguration getMensagens() {
		return msgs;
	}
	
	public void saveData() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("§cErro ao salvar o arquivo data.yml");
		}
	}
	
	public void saveMensagens() {
		try {
			msgs.save(mfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("§cErro ao salvar o arquivo data.yml");
		}
	}
}