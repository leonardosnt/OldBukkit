package com.outlook.devleeo.LsBans.storage;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsBans.LsBans;

public class SQLite {

	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	public static Plugin plugin;
	
	public static void setup() {
		if (!config.getBoolean("MySQL.Ativar")) {
			File bansdb = new File(plugin.getDataFolder(), "bans.db");
			if (!bansdb.exists()) { 
				try {
					bansdb.createNewFile();
				} catch (IOException e) {
					Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar o arquivo bans.db");
					e.printStackTrace();
				}
			}
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				LsBans.con = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "bans.db");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao fazer a conexao com o sqlite, Erro:");
				e.printStackTrace();
			}
			
			
			try (PreparedStatement ps = LsBans.con.prepareStatement("CREATE TABLE IF NOT EXISTS bans(nick varchar(16) NOT NULL, currentTime bigint, tempo bigint, motivo varchar(100), admin varchar(16) NOT NULL, banido boolean NOT NULL, ip varchar(30))");
				PreparedStatement ps2 = LsBans.con.prepareStatement("CREATE TABLE IF NOT EXISTS ip_history(nick varchar(16), ip varchar(30))");) {
				ps.execute();
				ps2.execute();
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar a tabela bans Erro:");
				e.printStackTrace();
			}
		}
	}
}
