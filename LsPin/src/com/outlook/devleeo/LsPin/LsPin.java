package com.outlook.devleeo.LsPin;

import java.nio.file.Files;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsPin.comandos.Comando;
import com.outlook.devleeo.LsPin.filesmanager.Config;
import com.outlook.devleeo.LsPin.filesmanager.Storage;
import com.outlook.devleeo.LsPin.listeners.PlayerListener;
import com.outlook.devleeo.LsPin.loginSystem.LoginSystem;
import com.outlook.devleeo.LsPin.loginSystem.plugins.AuthMe;
import com.outlook.devleeo.LsPin.pinManager.Pin;
import com.outlook.devleeo.LsPin.tabCompleter.PinTabCompleter;

public class LsPin extends JavaPlugin {
	
	private static LsPin instance;
	public static LoginSystem login;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bPlugin habilitado.");
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bVersao: " + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bCriador: DevLeeo");
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bContato: devleeo@outlook.com");
		
		AuthMe.hook();
		instance = this;
		saveDefaultConfig();
		Storage.setupFiles();
		Storage.iniciarTask();
		
		getCommand("pin").setExecutor(new Comando());
		getCommand("pin").setTabCompleter(new PinTabCompleter());
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		
		if (Files.exists(Storage.PINS_FILE)) {
			try {
				Pin.setPins(Storage.recuperarPins());
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao recuperar os Pins, Erro:");
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bPlugin desabilitado.");
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bVersao: " + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bCriador: DevLeeo");
		Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bContato: devleeo@outlook.com");
		
		if (Config.SALVAR_QUANDO_DESATIVAR && !Pin.getPins().isEmpty()) {
			try {
				Storage.armazenarPins(Pin.getPins());
				Bukkit.getConsoleSender().sendMessage(Config.getPrefix() + " §bPins salvos com sucesso.");
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao armazenar os Pins, Erro:");
				e.printStackTrace();
			}
		}
		
	}
	
	public static LsPin getInstance() {
		return instance;
	}
	
}
