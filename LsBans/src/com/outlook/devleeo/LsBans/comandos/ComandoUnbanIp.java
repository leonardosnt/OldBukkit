package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoUnbanIp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /unbanip <ip>");
				return true;
			} else {
				if (BanManager.isIpBanido(args[0])) {
					BanManager.unbanIp(args[0]);
					sender.sendMessage(MensagensManager.PREFIX + "§cIp desbanido.");
				} else {
					sender.sendMessage(MensagensManager.PREFIX + "§cEste ip não está banido.");
				}
			}
		}
		return false;
	}

}
