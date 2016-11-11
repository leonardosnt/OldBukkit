package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoBanIp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /banip <ip>");
				return true;
			} else {
				BanManager.banIp(args[0]);
				sender.sendMessage(MensagensManager.PREFIX + "§cIp banido.");
			}
		}
		return false;
	}

}
