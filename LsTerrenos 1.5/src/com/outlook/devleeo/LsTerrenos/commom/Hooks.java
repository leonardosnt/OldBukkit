package com.outlook.devleeo.LsTerrenos.commom;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.outlook.devleeo.LsTerrenos.Main;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Hooks {

	private static Economy eco;
	private static WorldGuardPlugin wg;
	private static WorldEditPlugin we;
	private static Permission perm;
	
	public static void setup() {
		Plugin wgp = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		if (wgp != null) {
			wg = (WorldGuardPlugin) wgp;
			Utils.log("WorldGuard encontrado!!");
		} else {
			Utils.logError("WorldGuard nao encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
		}
	
		Plugin wep = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if (wep != null) {
			we = (WorldEditPlugin) wep;
			Utils.log("WorldEdit encontrado!!");
		} else {
			Utils.logError("WorldEdit nao encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
		}
		
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			eco = (Economy)economyProvider.getProvider();
			Utils.log("Vault encontrado!!");
		} else {
			Utils.logError("Nenhum sistema de economia encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
		}
		
	    RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	    if (permissionProvider != null) {
	        perm = permissionProvider.getProvider();
	    } else {
			Utils.logError("Nenhum sistema de permissoes encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
	    }
	}
	
	public static Permission getPermissions() {
		return perm;
	}
	
	public static Economy getEconomy() {
		return eco;
	}
	
	public static WorldEditPlugin getWorldedit() {
		return we;
	}
	
	public static WorldGuardPlugin getWorldguard() {
		return wg;
	}
}
