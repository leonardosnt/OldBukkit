package com.outlook.devleeo.com.lsdestemido;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Economy eco;
	public static Main instance;
	
	public static void hookEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if(economyProvider != null){
			eco = (Economy)economyProvider.getProvider();
		}
	}
	
	public void onEnable(){
		getServer().getConsoleSender().sendMessage("§c[LsDestemido] §7Plugin habilitado by DevLeeo");
		
		hookEconomy();
		Main.instance = this;
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new Destemido(), this);
		getCommand("destemido").setExecutor(new Destemido());
	}
	
	public void onDisable(){
		getServer().getConsoleSender().sendMessage("§c[LsDestemido] §7Plugin habilitado by DevLeeo");
		HandlerList.unregisterAll(new Destemido());
	}

	public static Main getInstance(){
		return Main.instance;
	}
}
