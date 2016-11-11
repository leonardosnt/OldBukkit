package com.outlook.devleeo.LsBans;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsBans.comandos.ComandoBan;
import com.outlook.devleeo.LsBans.comandos.ComandoBanIp;
import com.outlook.devleeo.LsBans.comandos.ComandoCheckBans;
import com.outlook.devleeo.LsBans.comandos.ComandoKick;
import com.outlook.devleeo.LsBans.comandos.ComandoLsBans;
import com.outlook.devleeo.LsBans.comandos.ComandoUnban;
import com.outlook.devleeo.LsBans.comandos.ComandoUnbanIp;
import com.outlook.devleeo.LsBans.listeners.PlayerListener;
import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.storage.MySQL;
import com.outlook.devleeo.LsBans.storage.SQLite;

public class LsBans extends JavaPlugin {
	
	public static Connection con;
	public static String tabela;
	public static String tabelaIps;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 呂LsBans versao " + getDescription().getVersion() + " habilitado.");
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 佝Criador: DevLeeo");
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 佝Contato: DevLeeo@outlook.com");
		SQLite.plugin = this;
		BanManager.plugin = this;
		ComandoLsBans.plugin = this;
		saveDefaultConfig();
		MySQL.setup();
		SQLite.setup();
		
		getCommand("ban").setExecutor(new ComandoBan());
		getCommand("banip").setExecutor(new ComandoBanIp());
		getCommand("checkbans").setExecutor(new ComandoCheckBans());
		getCommand("kick").setExecutor(new ComandoKick());
		getCommand("unban").setExecutor(new ComandoUnban());
		getCommand("unbanip").setExecutor(new ComandoUnbanIp());
		getCommand("lsbans").setExecutor(new ComandoLsBans());
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		if (getConfig().getBoolean("MySQL.Ativar")) {
			tabela = MySQL.tabela;
			tabelaIps = MySQL.tabelaIps;
		} else {
			tabela = "bans";
			tabelaIps = "ip_history";
		}
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 呂LsBans versao "+getDescription().getVersion()+" desabilitado.");
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 佝Criador: DevLeeo");
		Bukkit.getConsoleSender().sendMessage("低[LsBans] 佝Contato: DevLeeo@outlook.com");
		deletarHistoricos();
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deletarHistoricos() {
		Path p = Paths.get(getDataFolder() + File.separator + "checkbans");
		if (Files.exists(p)) {
			try(DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {
				for (Path path : stream) {
					Files.delete(path);
				}
				Files.delete(p);
			} catch (IOException | DirectoryIteratorException e) {
				e.printStackTrace();
			}
		}
	}
}
