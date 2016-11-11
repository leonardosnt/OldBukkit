package com.outlook.devleeo.LsVipTrade.hooks.plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsVipTrade.hooks.VipSystem;
import com.outlook.devleeo.LsVipTrade.utils.Utils;

public class EasyVip extends Utils {
	
	private static Plugin easyVip = Bukkit.getPluginManager().getPlugin("EasyVIP");
	
	public static void hook() {
		if (easyVip != null) {
			Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aEasyVip encontrado, VipSystem = EasyVIP.");
			vipSystem = VipSystem.EASY_VIP;
			if (!easyVip.getConfig().getBoolean("flatfile")) {
				useMysql = true;
				Utils.url = easyVip.getConfig().getString("URL");
				Utils.user = easyVip.getConfig().getString("Username");
				Utils.senha = easyVip.getConfig().getString("Password");
			}
		}
	}
}
