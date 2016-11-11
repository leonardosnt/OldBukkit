package com.outlook.devleeo.LsVipTrade.hooks;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.outlook.devleeo.LsVipTrade.Main;

public class EconomyHook {

	public static Economy eco;
	
	public static void hook(){
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null){
			eco = (Economy)economyProvider.getProvider();
			Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aVault encontrado. Hooked (Economy)");
		} else {
			Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §cNenhum plugin de economia encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
		}
	}
}
