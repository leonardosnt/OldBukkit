package com.outlook.devleeo.LsMvP.outros;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Hooks {

	public static Economy eco;
	
	public static void hookEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null){
			eco = (Economy)economyProvider.getProvider();
			Bukkit.getConsoleSender().sendMessage("§3 Vault encontrado. §bHooked (Economy)");
		}
	}
}
