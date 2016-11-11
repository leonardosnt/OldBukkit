package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ComandoLsBans implements CommandExecutor {
	
	public static Plugin plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		sender.sendMessage("§4[LsBans]§c Informações:");
		sender.sendMessage("§4» §cCriador: DevLeeo");
		sender.sendMessage("§4» §cVersão: " + plugin.getDescription().getVersion());
		if (sender.hasPermission("lsbans.admin")) {
			sender.sendMessage("§4[LsBans]§c Comandos: ");
			sender.sendMessage("§4» §c /ban §7-§c Use para banir um jogador.");
			sender.sendMessage("§4» §c /unban §7-§c Use para desbanir um jogador.");
			sender.sendMessage("§4» §c /banip §7-§c Use para banir um ip.");
			sender.sendMessage("§4» §c /unbanip §7-§c Use para desbanir um ip.");
			sender.sendMessage("§4» §c /checkbans §7-§c Use para ver os bans de um jogador.");
			sender.sendMessage("§4» §c /kick §7-§c Use para kickar um jogador do servidor.");
		}
		return false;
	}

}
