package com.outlook.devleeo.legendchatExt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Main.config = this.getConfig();
		getCommand("chat").setExecutor(new Comandos());
		getServer().getPluginManager().registerEvents(new Listeners(), this);
	}
	
	@Override
	public void onDisable() {

	}
	
}
