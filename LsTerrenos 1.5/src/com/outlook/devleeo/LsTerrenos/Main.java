package com.outlook.devleeo.LsTerrenos;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsTerrenos.comandos.Comandos;
import com.outlook.devleeo.LsTerrenos.commom.Hooks;
import com.outlook.devleeo.LsTerrenos.listeners.PlayerListener;
import com.outlook.devleeo.LsTerrenos.managers.FilesManager;

public class Main extends JavaPlugin {

	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		plugin = this;
		FilesManager.setup(this);
		System.gc();
		System.runFinalization();
		
		getLogger().info("LsTerrenos v" + getDescription().getVersion() + " habilitado.");
		getLogger().info("Criador: DevLeeo");
		
		getCommand("terreno").setExecutor(new Comandos());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		Hooks.setup();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("LsTerrenos v" + getDescription().getVersion() + " habilitado.");
		getLogger().info("Criador: DevLeeo");
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
    
}
