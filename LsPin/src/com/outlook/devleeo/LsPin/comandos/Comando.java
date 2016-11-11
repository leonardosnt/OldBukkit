package com.outlook.devleeo.LsPin.comandos;

import static com.outlook.devleeo.LsPin.filesmanager.Config.ATIVAR_PIN_ATUAL;
import static com.outlook.devleeo.LsPin.filesmanager.Config.MAX_TENTATIVAS;
import static com.outlook.devleeo.LsPin.filesmanager.Config.TEMPO_BLOQUEIO;
import static com.outlook.devleeo.LsPin.filesmanager.Config.getMensagem;
import static com.outlook.devleeo.LsPin.filesmanager.Config.getPrefix;
import static com.outlook.devleeo.LsPin.utils.Utils.isAuthenticated;
import static com.outlook.devleeo.LsPin.utils.Utils.isAuthorized;
import static com.outlook.devleeo.LsPin.utils.Utils.isPlayer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsPin.LsPin;
import com.outlook.devleeo.LsPin.pinManager.Pin;

public class Comando implements CommandExecutor {
	
	private static final Map<Player, Integer> TENTATIVAS = new HashMap<>();

	@Override
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0 && !isAuthorized(sender)) {
			sender.sendMessage(getPrefix() + " §bComandos");
			sender.sendMessage(" §3/pin ativar §f-§b Ativa o pin em sua conta.");
			sender.sendMessage(" §3/pin recuperar §f-§b Recupera o pin da sua conta.");
			if (ATIVAR_PIN_ATUAL) {
				sender.sendMessage(" §3/pin atual §f-§b Ve o pin atual da sua conta.");
			}
			return true;
		} else if (args.length == 0 && isAuthorized(sender)) {
			sender.sendMessage(getPrefix() + " §bComandos");
			sender.sendMessage(" §3/pin ativar §f-§b Ativa o pin em sua conta.");
			sender.sendMessage(" §3/pin recuperar §f-§b Recupera o pin da sua conta.");
			sender.sendMessage(" §3/pin atual §f-§b Ve o pin atual da sua conta.");
			sender.sendMessage(" §3/pin remover <Player> §f-§b Remove o pin de uma conta.");
			sender.sendMessage(" §3/pin ver <Player> §f-§b Ve o pin de uma determinada conta.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("ativar")) {
			if (isPlayer(sender)) {
			 	if (!isAuthenticated(sender)) {
					sender.sendMessage(getMensagem("precisa_logado"));
					return true;
			 	}
				if (Pin.hasPin(sender.getName())) {
					sender.sendMessage(getMensagem("ja_possui_pin"));
					return true;
		 		}
				Pin.ativar(sender.getName());
				((Player) sender).kickPlayer(getMensagem("pin_ativado").replace("<pin>", Pin.getPin(sender.getName())));
			}
		}
		
		if (args[0].equalsIgnoreCase("atual")) {
			if (isPlayer(sender)) {
				if (!isAuthenticated(sender)) {
					sender.sendMessage(getMensagem("precisa_logado"));
					return true;
				}
				if (Pin.hasPin(sender.getName())) {
					if (ATIVAR_PIN_ATUAL) {
						((Player) sender).kickPlayer(getMensagem("pin_atual").replace("<pin>", Pin.getPin(sender.getName())));
					}
				} else {
					sender.sendMessage(getMensagem("nao_possui_pin"));
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("recuperar")) {
			if (isPlayer(sender)) {
				if (args.length == 1 || args.length > 2) {
					sender.sendMessage(getPrefix() + " §bUse /pin recuperar <Pin>");
					return true;
				} else {
					if (Pin.hasPin(sender.getName())) {
						String pinDigitado = args[1];
						String pin = Pin.getPin(sender.getName());
						
						if (TENTATIVAS.containsKey((Player)sender) && TENTATIVAS.get((Player)sender) == MAX_TENTATIVAS) {
							sender.sendMessage(getMensagem("atingiu_max_tentativas"));
							return true;
						}
						
						if (pin.equals(pinDigitado)) {
							String novaSenha = Pin.gerarSenha();
							Pin.removePin(sender.getName());
							((Player)sender).kickPlayer(getMensagem("Conta_recuperada").replace("<senha>", novaSenha));
							/* CHANGEPASSWORD */
						} else {
							if (TENTATIVAS.containsKey((Player)sender)) {
								TENTATIVAS.put((Player)sender, TENTATIVAS.get((Player)sender) + 1);
							} else {
								TENTATIVAS.put((Player)sender, 1);
								new BukkitRunnable() {
									
									@Override
									public void run() {
										TENTATIVAS.remove((Player)sender);
									}
								}.runTaskLater(LsPin.getInstance(), TEMPO_BLOQUEIO);
							}
							((Player)sender).kickPlayer(getMensagem("pin_incorreto"));
						}
					} else {
						sender.sendMessage(getMensagem("nao_possui_pin"));
					}
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("ver")) {
			if (isAuthorized(sender)) {
				if (args.length == 1) {
					sender.sendMessage(getPrefix() + " §bUse /pin ver <Player>");
				} else if (args.length == 2) {
					String target = args[1];
					if (Pin.hasPin(target)) {
						sender.sendMessage(getPrefix() + "§b Pin de " + target + ": §c" + Pin.getPin(target));
					} else {
						sender.sendMessage(getMensagem("nao_possui_pin"));
					}
				}
			}
		}
		
		if (args[0].equalsIgnoreCase("remover")) {
			if (isAuthorized(sender)) {
				if (args.length == 1 || args.length > 2) {
					sender.sendMessage(getPrefix() + " §bUse /pin remover <Player>");
					return true;
				} else {
					String target = args[1];
					if (Pin.hasPin(target)) {
						Pin.removePin(target);
						sender.sendMessage(getPrefix() + " §bPin de §c" + target + " §bremovido com sucesso.");
					} else {
						sender.sendMessage(getMensagem("nao_possui_pin"));
					}
				}
			}
		}
		
		return false;
	}

}
