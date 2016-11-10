package com.outlook.devleeo.lsx1.outros;

import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Hooks {

	public static SimpleClans sc;
	public static Economy eco;
	
	public static void hookSimpleClans(){
	    if(Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null){
		       sc = ((SimpleClans)Bukkit.getServer().getPluginManager().getPlugin("SimpleClans"));
		    }
	}
	
	public static void hookEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if(economyProvider != null){
			Bukkit.getConsoleSender().sendMessage("§3[Ls_x1] §bVault encontrado. Hooked (Economy)");
			eco = (Economy)economyProvider.getProvider();
		}
		
	}
	

}
