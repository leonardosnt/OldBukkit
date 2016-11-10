package com.outlook.devleeo.LsMvP;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsMvP.chat.DefaultChat;
import com.outlook.devleeo.LsMvP.chat.LegendChat;
import com.outlook.devleeo.LsMvP.comandos.Comandos;
import com.outlook.devleeo.LsMvP.files.Files;
import com.outlook.devleeo.LsMvP.listeners.BossListener;
import com.outlook.devleeo.LsMvP.listeners.PlayerListener;
import com.outlook.devleeo.LsMvP.outros.AutoStart;
import com.outlook.devleeo.LsMvP.outros.Hooks;
import com.outlook.devleeo.LsMvP.tabcompleter.MvpTabCompleter;

public class Main extends JavaPlugin {
	
	Files files = Files.getInstance();
	private static Main instance;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		files.setupFiles(this);
		Main.instance = this;
		setupChat();
		getCommand("mvp").setExecutor(new Comandos());
		
		if (getConfig().getBoolean("AutoStart.Ativado")) {
			AutoStart.checkTempo();
		}
		
		if (getConfig().getBoolean("AtivarTabCompleter")) {
			getCommand("mvp").setTabCompleter(new MvpTabCompleter());
		}
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new BossListener(), this);
		
		Bukkit.getConsoleSender().sendMessage("§3--------==========[§bLsMobsVsPlayers§3]==========--------");
		Bukkit.getConsoleSender().sendMessage("§3 Status: §ahabilitado");
		Bukkit.getConsoleSender().sendMessage("§3 Versao: §b" + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§3 Autor: §bDevLeeo");
		Bukkit.getConsoleSender().sendMessage("§3 Contato: §bdevleeo@outlook.com");
		Hooks.hookEconomy();
		Bukkit.getConsoleSender().sendMessage("§3--------==========[§bLsMobsVsPlayers§3]==========--------");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§3--------==========[§bLsMobsVsPlayers§3]==========--------");
		Bukkit.getConsoleSender().sendMessage("§3 Status: §cdesabilitado");
		Bukkit.getConsoleSender().sendMessage("§3 Versao: §b" + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§3 Autor: §bDevLeeo");
		Bukkit.getConsoleSender().sendMessage("§3 Contato: §bdevleeo@outlook.com");
		Bukkit.getConsoleSender().sendMessage("§3--------==========[§bLsMobsVsPlayers§3]==========--------");
	}
	
	public static Main getInstance() {
		return Main.instance;
	}
	
	private void setupChat() {
		if (Bukkit.getPluginManager().getPlugin("Legendchat") != null) {
			Bukkit.getPluginManager().registerEvents(new LegendChat(), this);
		} else {
			Bukkit.getPluginManager().registerEvents(new DefaultChat(), this);
		}
	}
	
}
