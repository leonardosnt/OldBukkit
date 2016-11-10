package com.outlook.devleeo.LsFight;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsFight.comandos.Comandos;
import com.outlook.devleeo.LsFight.listeners.Chat;
import com.outlook.devleeo.LsFight.listeners.PlayerListener;
import com.outlook.devleeo.LsFight.outros.Files;
import com.outlook.devleeo.LsFight.outros.Hooks;
import com.outlook.devleeo.LsFight.tabcompleter.FightTabCompleter;
import com.outlook.devleeo.LsFight.utils.AutoStart;

public class LsFight extends JavaPlugin {
	
	private static LsFight instance;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		LsFight.instance = this;
		
		Bukkit.getConsoleSender().sendMessage("§5===============[§cLsFight§5]===============");
		Bukkit.getConsoleSender().sendMessage(" §5Status: §aHabilitado");
		Bukkit.getConsoleSender().sendMessage(" §5Criador: §cDevLeeo");
		Bukkit.getConsoleSender().sendMessage(" §5Versao: §c" + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage(" §5Contato: §cDevLeeo@outlook.com");
		Hooks.hookEconomy();
		Hooks.hookSimpleClans();
		Files.getInstance().setupFiles(this);
		Bukkit.getConsoleSender().sendMessage("§5===============[§cLsFight§5]===============");
		
		getCommand("fight").setExecutor(new Comandos());
		getCommand("fight").setTabCompleter(new FightTabCompleter());
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		if (Bukkit.getPluginManager().getPlugin("Legendchat") != null) {
			Bukkit.getPluginManager().registerEvents(new Chat(), this);
		}
		
		if (getConfig().getBoolean("AutoStart.Ativar")) {
			AutoStart.checkTempo();
		}
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§5===============[§cLsFight§5]===============");
		Bukkit.getConsoleSender().sendMessage(" §5Status: §cDesabilitado");
		Bukkit.getConsoleSender().sendMessage(" §5Criador: §cDevLeeo");
		Bukkit.getConsoleSender().sendMessage(" §5Versao: §c" + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage(" §5Contato: §cDevLeeo@outlook.com");
		Bukkit.getConsoleSender().sendMessage("§5===============[§cLsFight§5]===============");
	}
	
	public static LsFight getInstance() {
		return LsFight.instance;
	}
	
}
