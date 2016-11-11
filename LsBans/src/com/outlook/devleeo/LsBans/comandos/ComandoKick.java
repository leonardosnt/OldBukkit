package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoKick implements CommandExecutor {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /kick <jogador> <motivo>");
				return true;
			} else {
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					sender.sendMessage(MensagensManager.PREFIX + "§cJogador não encontrado.");
					return true;
				} else {
					String motivo = "";
					for (int i = 1; i < args.length; i++) {
						motivo += args[i] + " ";
					}
					if (motivo == "") {
						motivo = "Não especificado.";
					}
					p.kickPlayer(MensagensManager.getMensagem("Kick.Mensagem").replace("<admin>", sender.getName()).replace("<motivo>", motivo));
					if (config.getBoolean("Kick.Enviar_Mensagem_Global")) {
						Bukkit.broadcastMessage(MensagensManager.getMensagem("Kick.Global").replace("<admin>", sender.getName()).replace("<motivo>", motivo).replace("<player>", args[0]));
					}
				}
			}
		}
		return false;
	}

}
