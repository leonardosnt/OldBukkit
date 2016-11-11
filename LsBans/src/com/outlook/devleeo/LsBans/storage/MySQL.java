package com.outlook.devleeo.LsBans.storage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.outlook.devleeo.LsBans.LsBans;

public class MySQL {

	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	public static String tabela;
	public static String tabelaIps;
	
	public static void setup() {
		if (config.getBoolean("MySQL.Ativar")) {
			try {
				LsBans.con = DriverManager.getConnection("jdbc:mysql://" + config.getString("MySQL.Host") + ":" + config.getString("MySQL.Porta") + "/" + config.getString("MySQL.Database"), config.getString("MySQL.Usuario"), config.getString("MySQL.Senha"));
				Bukkit.getConsoleSender().sendMessage("§2[LsBans] §aConexao com mysql estabelecida com sucesso.");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao fazer a conexao com o mysql, Erro:");
				e.printStackTrace();
			}
			
			tabela = config.getString("MySQL.Tabela_Banimentos");
			tabelaIps = config.getString("MySQL.Tabela_Ips");
			
			try (PreparedStatement ps = LsBans.con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tabela + "(nick varchar(16) NOT NULL, currentTime bigint, tempo bigint, motivo varchar(100), admin varchar(16) NOT NULL, banido boolean NOT NULL, ip varchar(30))");
				PreparedStatement ps2 = LsBans.con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tabelaIps + "(nick varchar(16), ip varchar(30))");) {
				ps.execute();
				ps2.execute();
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar a tabela " + tabela + " Erro:");
				e.printStackTrace();
			}
		}
	}
}
