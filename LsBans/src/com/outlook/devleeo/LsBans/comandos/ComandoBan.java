package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoBan implements CommandExecutor {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /ban <jogador> <tempo> <motivo>");
				return true;
			}
			
			if (args.length == 1) {
				BanManager.banPlayer(args[0], config.getInt("Ban.Tempo_Default"), sender.getName(), MensagensManager.getMensagem("Motivo_Default"));
				sender.sendMessage(MensagensManager.PREFIX + "§cJogador banido.");
			}
			if (args.length == 2) {
				if (Utils.isNumber(args[1])) {
					BanManager.banPlayer(args[0], Integer.parseInt(args[1]), sender.getName(), MensagensManager.getMensagem("Motivo_Default"));
					sender.sendMessage(MensagensManager.PREFIX + "§cJogador banido.");
					return true;
				} else {
					sender.sendMessage(MensagensManager.PREFIX + "§cUse /ban <jogador> <tempo> <motivo>");
				}
			}
			if (args.length >= 3) {
				String motivo = "";
				for (int i = 2; i < args.length; i++) {
					motivo = motivo + args[i] + " ";
				}
				try {
					BanManager.banPlayer(args[0], Integer.parseInt(args[1]), sender.getName(), motivo);
					sender.sendMessage(MensagensManager.PREFIX + "§cJogador banido.");
				} catch (Exception e) {
					sender.sendMessage(MensagensManager.PREFIX + "§cUse /ban <jogador> <tempo> <motivo>");
				}
			}
		}
		return false;
	}

}
