package com.outlook.devleeo.LsPin.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsPin.LsPin;
import com.outlook.devleeo.LsPin.filesmanager.Config;

import fr.xephi.authme.cache.auth.PlayerCache;

public class Utils {
	
	public static boolean isAuthorized(CommandSender sender) {
		if (sender.hasPermission("lspin.admin")) {
			return true;
		} else {
			sender.sendMessage(Config.getPrefix() + " §cVocê não tem acesso a este comando.");
			return false;
		}
	}
	
	public static boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage("§cEste comando nao pode ser executado pelo console.");
			return false;
		}
	}
	
	public static boolean isAuthenticated(CommandSender sender) {
		switch (LsPin.login) {
			case AuthMe1:
			case AuthMe2:
				return PlayerCache.getInstance().isAuthenticated(sender.getName().toLowerCase());
			case xAuth1:
			case xAuth2:
				break;
			default:
				break;
		}
		return false;
	}

	public static void changePassword(Player player, String senha) {
		switch (LsPin.login) {
			case AuthMe1:
			case AuthMe2:
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "authme changepassword " + player.getName() + " " + senha);
				break;
			case xAuth1:
			case xAuth2:
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "xauth2");
				break;
			default:
				break;
		}
	}
}
