package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoUnban implements CommandExecutor {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /unban <jogador>");
				return true;
			}
			
			if (BanManager.isBanido(args[0])) {
				BanManager.unbanPlayer(args[0]);
				sender.sendMessage(MensagensManager.PREFIX + "§aJogador desbanido.");
				if (config.getBoolean("Unban.Enviar_Mensagem_Global")) {
					Bukkit.broadcastMessage(MensagensManager.getMensagem("Unban.Global").replace("<player>", args[0]).replace("<admin>", sender.getName()));
				}
			} else {
				sender.sendMessage(MensagensManager.PREFIX + "§cEste jogador não está banido.");
			}
		}
		return false;
	}

}
