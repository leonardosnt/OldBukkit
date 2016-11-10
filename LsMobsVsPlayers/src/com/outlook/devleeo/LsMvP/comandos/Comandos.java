package com.outlook.devleeo.LsMvP.comandos;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.*;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsMvP.Main;

public class Comandos implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String LsCommand, String[] args) {
		
		if (args.length == 0) {
			if (!sender.hasPermission("lsmvp.admin")) {
				sender.sendMessage(formatMessage("{prefix} Comandos:"));
				sender.sendMessage(" §f» §3/mvp participar §f- §bUse para entrar no evento.");
				sender.sendMessage(" §f» §3/mvp status §f- §bUse para ver o status do evento.");
				sender.sendMessage(" §f» §3/mvp sair §f- §bUse para sair do evento.");
				return true;
			}
			
			if (sender.hasPermission("lsmvp.admin")) {
				sender.sendMessage(formatMessage("{prefix} Comandos:"));
				sender.sendMessage(" §f» §3/mvp participar §f- §bUse para entrar no evento.");
				sender.sendMessage(" §f» §3/mvp status §f- §bUse para ver o status do evento.");
				sender.sendMessage(" §f» §3/mvp sair §f- §bUse para sair do evento.");
				sender.sendMessage(" §f» §3/mvp setspawnboss §f- §bSeta o spawn do boss.");
				sender.sendMessage(" §f» §3/mvp setentrada §f- §bSeta a entrada do evento.");
				sender.sendMessage(" §f» §3/mvp setsaida §f- §bSeta a saida do evento.");
				sender.sendMessage(" §f» §3/mvp finiciar §f- §bForça a inicialização evento.");
				sender.sendMessage(" §f» §3/mvp ffinalizar §f- §bForça a finalização do evento.");
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("setspawnboss")) {// #####  setspawnboss ######
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setSpawnBoss((Player)sender);
					sender.sendMessage(formatMessage("{prefix} Spawn do boss definido!"));
				}
			}
		}
				
		if (args[0].equalsIgnoreCase("setentrada")) {// #####  SETENTRADA ######		
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setEntrada((Player)sender);
					sender.sendMessage(formatMessage("{prefix} Entrada do evento definida!"));
				}
			}
		}
			
		if (args[0].equalsIgnoreCase("setsaida")) {// #####  SETSAIDA ######
			if (isAuthorized(sender)) {
				if (isPlayer(sender)) {
					setSaida((Player)sender);
					sender.sendMessage(formatMessage("{prefix} Saida do evento definida!"));
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("finiciar")) { // #####  INICIAR ######
			if (isAuthorized(sender)) {
				if (isIniciado()) {
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("JaFoiIniciado")));
					return true;
				}
				
				if (getEntrada() == null || getSaida() == null || getSpawnMob() == null) {
					sender.sendMessage(formatMessage("{prefix} &cVocê precisa definir a entrada, saida e o spawn do boss antes de iniciar o evento."));
					return true;
				}
				
				preparar(Main.getInstance().getConfig().getInt("Iniciando.NumeroDeAvisos"));
				setIniciado(true);
				setAcontecendo(true);
			}
		}
		
		if (args[0].equalsIgnoreCase("ffinalizar")) { // #####  finalizar ######
			if (isAuthorized(sender)) {
				if (!isIniciado()) {
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("NaoAcontecendo")));
					return true;
				}
				
				finalizarMvP();
				for(String msg : Main.getInstance().getConfig().getStringList("FinalizadorPorAdmin")) {
					Bukkit.broadcastMessage(formatMessage(msg));
				}
			}
		}
			
		if (args[0].equalsIgnoreCase("participar")) {
			if (isPlayer(sender)) {
				if (isAcontecendo()) {
					if (participantes.contains((Player)sender)) {
						sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("JaParticipando")));
						return true;
					}
					
					teleportEntrada((Player)sender);
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("EntrouNoEvento")));
					participantes.add((Player)sender);
					
				} else {
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("NaoAcontecendo")));
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("sair")) {
			if (isPlayer(sender)) {
				if (participantes.contains((Player)sender)) {
					teleportSaida((Player)sender);
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("Saiu")));
				} else {
					sender.sendMessage(formatMessage(Main.getInstance().getConfig().getString("NaoParticipando")));
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("status")) {
			for(String msg : Main.getInstance().getConfig().getStringList("EventoStatus")) {
				sender.sendMessage(formatMessage(msg).replace("%participantes%", String.valueOf(participantes.size())).replace("%>>%", "»").replace("%acontecendo%", String.valueOf(isAcontecendo()).replace("true", "Sim").replace("false", "Não")).replace("%vida%", Integer.toString(getBossHealth())));
			}
		}
		
		return false;
	}
}
