package com.outlook.devleeo.LsFight.comandos;

import static com.outlook.devleeo.LsFight.utils.FightUtils.finalizar;
import static com.outlook.devleeo.LsFight.utils.FightUtils.iniciando;
import static com.outlook.devleeo.LsFight.utils.FightUtils.isAberto;
import static com.outlook.devleeo.LsFight.utils.FightUtils.isAcontecendo;
import static com.outlook.devleeo.LsFight.utils.FightUtils.participantes;
import static com.outlook.devleeo.LsFight.utils.LocationUtils.setEntrada;
import static com.outlook.devleeo.LsFight.utils.LocationUtils.setPos1;
import static com.outlook.devleeo.LsFight.utils.LocationUtils.setPos2;
import static com.outlook.devleeo.LsFight.utils.LocationUtils.setSaida;
import static com.outlook.devleeo.LsFight.utils.MensagensUtils.formatMessage;
import static com.outlook.devleeo.LsFight.utils.MensagensUtils.getFinalizadoPorAdmMensagens;
import static com.outlook.devleeo.LsFight.utils.MensagensUtils.getMensagem;
import static com.outlook.devleeo.LsFight.utils.MensagensUtils.getPrefix;
import static com.outlook.devleeo.LsFight.utils.MensagensUtils.getStatusTemplate;
import static com.outlook.devleeo.LsFight.utils.PlayerUtils.isAuthorized;
import static com.outlook.devleeo.LsFight.utils.PlayerUtils.isPlayer;
import static com.outlook.devleeo.LsFight.utils.TeleportUtils.teleportEntrada;
import static com.outlook.devleeo.LsFight.utils.TeleportUtils.teleportSaida;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsFight.LsFight;
import com.outlook.devleeo.LsFight.outros.Files;
import com.outlook.devleeo.LsFight.utils.MensagensUtils;
import com.outlook.devleeo.LsFight.utils.PlayerUtils;

public class Comandos implements CommandExecutor {
	
	static Files files = Files.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lsCommand, String[] args) {
		
		if (args.length == 0) {
			if (args.length == 0) {
				for (String s : files.getMensagensFile().getStringList("USAGE")) {
					if (!sender.hasPermission("lsfight.admin") && (s.contains("set") || s.contains("Iniciar") || s.contains("Finalizar"))) {
						return true;
					}
					sender.sendMessage(MensagensUtils.formatMessage(s));
				}
				return true;
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("setentrada")) {
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setEntrada((Player)sender);
					sender.sendMessage(getPrefix() + " §cEntrada definida com sucesso.");
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("setsaida")) {
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setSaida((Player)sender);
					sender.sendMessage(getPrefix() + " §cSaida definida com sucesso.");
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("setpos1")) {
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setPos1((Player)sender);
					sender.sendMessage(getPrefix() + " §cPosição 1 definida com sucesso.");
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("setpos2")) {
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setPos2((Player)sender);
					sender.sendMessage(getPrefix() + " §cPosição 2 definida com sucesso.");
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("finiciar")) {
			if (isAuthorized(sender)) {
				if (files.getDataFile().getString("Entrada.") == null || files.getDataFile().getString("Saida.") == null || files.getDataFile().getString("Pos1.") == null || files.getDataFile().getString("Pos2.") == null) {
					sender.sendMessage(getPrefix() + " §cVocê precisa definir a entrada, saida, posição 1 e posição 2 antes de iniciar o evento.");
					return true;
				}
				if (isAcontecendo()) {
					sender.sendMessage(getPrefix() + " §c O evento já está acontecendo.");
					return true;
				}
				iniciando(LsFight.getInstance().getConfig().getInt("Iniciando.NumeroDeAvisos"));
			}
		}
		
		if (args[0].equalsIgnoreCase("ffinalizar")) {
			if (isAuthorized(sender)) {
				if (isAcontecendo()) {
					for (Player p : participantes) {
						teleportSaida(p);
					}
					
					finalizar();
					
					for (String msg : getFinalizadoPorAdmMensagens()) {
						Bukkit.broadcastMessage(formatMessage(msg));
					}
				} else {
					sender.sendMessage(getMensagem("NAO_ACONTECENDO"));
				}
			}
		}
		
		/*            COMANDOS MEMBROS                */
		
		if (args[0].equalsIgnoreCase("participar")) {
			if (isPlayer(sender)) {
				Player p = (Player)sender;
				if (!isAcontecendo()) {
					p.sendMessage(getMensagem("NAO_ACONTECENDO"));
					return true;
				} else if (!isAberto()) {
					p.sendMessage(getMensagem("NAO_ABERTO"));
					return true;
				} else if (participantes.contains(sender)) {
					p.sendMessage(getMensagem("JA_PARTICIPANDO"));
					return true;
				}
				if (!PlayerUtils.isInventoryEmpty(p)) {
					p.sendMessage(getMensagem("ESVAZIE_INV"));
					return true;
				}
				teleportEntrada((Player)sender);
				participantes.add((Player)sender);
				sender.sendMessage(getMensagem("ENTROU_NO_EVENTO"));
			}
		}
		
		if (args[0].equalsIgnoreCase("status")) {
			for (String msg : getStatusTemplate()) {
				sender.sendMessage(formatMessage(msg));
			}
		}
	
		return false;
	}

}
