package com.outlook.devleeo.LsVipTrade.hooks.plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsVipTrade.hooks.VipSystem;
import com.outlook.devleeo.LsVipTrade.utils.Utils;

public class VipZero extends Utils {
	
	private static Plugin vipZero = Bukkit.getPluginManager().getPlugin("VipZero");
	
	public static void hook() {
		if (vipZero != null) {
			Bukkit.getConsoleSender().sendMessage("§2[LsVipTrade] §aVipZero encontrado, VipSystem = VipZero.");
			vipSystem = VipSystem.VIP_ZERO;
			if (vipZero.getConfig().getBoolean("MySQL.use")) {
				useMysql = true;
				setupMysql(vipZero.getConfig().getString("MySQL.Host"), vipZero.getConfig().getString("MySQL.Port"),vipZero.getConfig().getString("MySQL.Username"),vipZero.getConfig().getString("MySQL.Password"),vipZero.getConfig().getString("MySQL.Database"));
			}
		}
	}
}
