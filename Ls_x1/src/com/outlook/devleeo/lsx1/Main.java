package com.outlook.devleeo.lsx1;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.lsx1.comandos.Comandos;
import com.outlook.devleeo.lsx1.listeners.PlayerListener;
import com.outlook.devleeo.lsx1.outros.Data;
import com.outlook.devleeo.lsx1.outros.Hooks;

public class Main extends JavaPlugin{
	
	private static Main instance;
	Data data = Data.getInstance();
	
	public void onEnable(){
		Hooks.hookEconomy();
		Hooks.hookSimpleClans();
		Main.instance = this;
		data.setup(this);
		saveDefaultConfig();
		
		Bukkit.getServer().getConsoleSender().sendMessage("§3[Ls_x1] §bPlugin habilitado (Criador: DevLeeo)");
		
		getCommand("x1").setExecutor(new Comandos());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public void onDisable(){
		Bukkit.getServer().getConsoleSender().sendMessage("§3[Ls_x1] §bPlugin desabilitado (Criador: DevLeeo)");
	}
	
	public static Main getInstance(){
		return Main.instance;
	}
}
