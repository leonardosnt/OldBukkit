package com.outlook.devleeo.LsBans.outros;

import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;

public class Utils {

	public static boolean isNumber(String n) {
		return n.matches("\\d*");
	}
	
	public static boolean isAuthorized(CommandSender sender) {
		if (sender.hasPermission("lsban.admin")) {
			return true;
		} else {
			sender.sendMessage(MensagensManager.PREFIX + "§cVocê não tem acesso a este comando.");
			return false;
		}
	}
	
	public static long getTempoRestante(String nome) {
		long agora = (System.currentTimeMillis() / 1000 / 60);
		long banido = Long.parseLong(BanManager.getBan(nome).split(";")[0]);
		return (agora - banido);
	}
}
