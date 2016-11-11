package com.outlook.devleeo.LsBans.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.Utils;

public class ComandoCheckBans implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 0) {
				sender.sendMessage(MensagensManager.PREFIX + "§cUse /checkbans <jogador> <-h>");
				return true;
			}
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("-h")) {
					if (BanManager.isBanido(args[0])) {
						BanManager.gerarHistorico(args[0]);
						sender.sendMessage(MensagensManager.PREFIX + "§cFoi criado um arquivo em §7plugins/LsBans/checkbans §ccom o histórico.");
					} else {
						sender.sendMessage(MensagensManager.PREFIX + "§cNenhum dado encontrado sobre este jogador.");
					}
					return true;
				}
			}
			if (BanManager.getBans(args[0]) == -1) {
				sender.sendMessage(MensagensManager.PREFIX + "§cNenhum dado encontrado sobre este jogador.");
			} else {
				sender.sendMessage(MensagensManager.PREFIX + "§cInformações sobre " + args[0]);
				sender.sendMessage("  §cEste jogador já foi banido §7"+BanManager.getBans(args[0])+"§c vez(es)");
				sender.sendMessage("  §cAtualmente este jogador "+Boolean.toString(BanManager.isBanido(args[0])).replace("true", "").replace("false", "não ")+"está banido.");
				sender.sendMessage("  §cUltimos banimentos:");
				BanManager.getLastBans(sender, args[0]);
				sender.sendMessage(MensagensManager.PREFIX + "§c Para ver o historico completo de bans sobre este jogador digite §7/checkbans <jogador> -h");
			}
		}
		return false;
	}

}
