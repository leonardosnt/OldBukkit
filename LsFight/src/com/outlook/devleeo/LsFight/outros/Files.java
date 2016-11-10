package com.outlook.devleeo.LsFight.outros;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Files {
	
	private static Files instance = new Files();
	private FileConfiguration data;
	private File dfile;
	private FileConfiguration msgs;
	private File mfile;
	
	public static Files getInstance() {
		return instance;
	}
	
	public void setupFiles(Plugin p) {
		
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		
		dfile = new File(p.getDataFolder(), "Data.yml");
		
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
				Bukkit.getConsoleSender().sendMessage(" §5Files: §cData.yml criado com sucesso.");
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao criar o arquivo data.yml, Erro:");
				e.printStackTrace();
			}
		}
		
		data = YamlConfiguration.loadConfiguration(dfile);
		
		/* Fim Data, Inicio Mensagens */
		
		mfile = new File(p.getDataFolder(), "Mensagens.yml");
		
		if (!mfile.exists()) {
			try {
				mfile.createNewFile();
				p.saveResource("Mensagens.yml", true);
				Bukkit.getConsoleSender().sendMessage(" §5Files: §cMensagens.yml criado com sucesso.");
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao criar o arquivo mensagens.yml, Erro:");
				e.printStackTrace();
			}
		}
		
		msgs = YamlConfiguration.loadConfiguration(mfile);
	}

	public FileConfiguration getDataFile() {
		return data;
	}
	
	public FileConfiguration getMensagensFile() {
		return msgs;
	}
	
	public void saveDataFile() {
		try {
			data.save(dfile);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao salvar o arquivo data.yml, Erro:");
			e.printStackTrace();
		}
	}
	
	public void saveMensagensFile() {
		try {
			msgs.save(mfile);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao salvar o arquivo mensagens.yml, Erro:");
			e.printStackTrace();
		}
	}
}
