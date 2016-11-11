package com.outlook.devleeo.LsVipTrade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.devleeo.LsVipTrade.comandos.Comandos;
import com.outlook.devleeo.LsVipTrade.hooks.EconomyHook;
import com.outlook.devleeo.LsVipTrade.hooks.plugins.EasyVip;
import com.outlook.devleeo.LsVipTrade.hooks.plugins.VipZero;
import com.outlook.devleeo.LsVipTrade.listeners.PlayerListener;
import com.outlook.devleeo.LsVipTrade.utils.Utils;

public class Main extends JavaPlugin {

	private static Main instance;
	public static Connection con;
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aPlugin Habilitado.");
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aCriador: §fDevLeeo");
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aContato: §fDevLeeo@outlook.com");
		
		EconomyHook.hook();
		VipZero.hook();
		EasyVip.hook();
		
		getCommand("vendervip").setExecutor(new Comandos());
		getCommand("comprarvip").setExecutor(new Comandos());
		getCommand("cancelarVenda").setExecutor(new Comandos());
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		
		if (Utils.useMysql) {
			try {
				con = DriverManager.getConnection(Utils.url, Utils.user, Utils.senha);
				Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aConexao com mysql bem sucedida.");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsVipTrade] §cNao foi possivel se conectar ao mysql. Erro:");
				Utils.useMysql = false;
				e.printStackTrace();
			}
		}
		
		if (Utils.vipSystem == null) {
			Bukkit.getConsoleSender().sendMessage("§4[LsVipTrade] §cNenhum plugin de vip encontrado, desativando plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aPlugin Desabilitado.");
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aCriador: §fDevLeeo");
		Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aContato: §fDevLeeo@outlook.com");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
}
