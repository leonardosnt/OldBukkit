package com.outlook.devleeo.LsTerrenos.comandos;

import static com.outlook.devleeo.LsTerrenos.commom.Utils.*;
import static com.outlook.devleeo.LsTerrenos.managers.TerrenosManager.*;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsTerrenos.Main;
import com.outlook.devleeo.LsTerrenos.commom.Hooks;
import com.outlook.devleeo.LsTerrenos.managers.FilesManager;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Comandos implements CommandExecutor {
	
	private static Set<String> deleteConfim = new HashSet<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (args.length > 0) {
			switch (args[0]) {
				case "comprar":
				case "vender":
				case "pvp":
				case "blockcmds":
				case "info":
				case "tp":
				case "lista":
				case "addamigo":
				case "delamigo":
				case "admin":
					if (!isPlayer(sender)) {
						sender.sendMessage("Este comando nao pode ser executado pelo console.");
					}
					break;
				case "reload":
				case "deletar":
					break;
				default:
					sendHelp(sender);
					break;
			}
		} else {
			sendHelp(sender);
			return true;
		}
		
		if (!isAuthorized(sender, "blockcmds") && !isAuthorized(sender, "pvp") && !isAuthorized(sender, "comprar") && !isAuthorized(sender, "vender") && !isAuthorized(sender, "deletar") && !isAuthorized(sender, "addamigo") && !isAuthorized(sender, "delamigo") && !isAuthorized(sender, "info") && !isAuthorized(sender, "tp") && !isAuthorized(sender, "lista")) {
			sender.sendMessage(getMensagem("sem_permissao"));
			return true;
		}
		
		if (isPlayer(sender)) {
			Player p = (Player)sender;
			
			if (args[0].equalsIgnoreCase("comprar")) {
				if (isAuthorized(p, "comprar")) {
					if (!isAdmin(p) && isWorldBlocked(p.getWorld())) {
						p.sendMessage(getMensagem("mundo_bloqueado"));
						return true;
					}
					if (args.length < 3) {
						p.sendMessage(getMensagem("comprar_usage"));
						return true;
					}
					if (args[2].matches("(?U)[^\\W_]*")) {
						try {
							comprarTerreno(p, Integer.parseInt(args[1]), args[2]);
						} catch (NumberFormatException e) {
							p.sendMessage(getMensagem("comprar_usage"));
						}
					} else {
						p.sendMessage(getMensagem("apenas_letras"));
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
			
			if (args[0].equalsIgnoreCase("info")) {
				if (isAuthorized(p, "info")) {
					sendInfo(p);
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
			
			if (args[0].equalsIgnoreCase("lista")) {
				if (args.length == 1) {
					if (isAuthorized(p, "lista")) {
						StringBuilder terrenos = new StringBuilder();
						if (getListaTerrenos(p.getName()).isEmpty()) {
							p.sendMessage(getMensagem("nao_possui_terreno"));
							return true;
						}
						for (String s : getListaTerrenos(p.getName())) {
							terrenos.append(s + ", ");
						}
						p.sendMessage(getMensagem("terreno_lista").replaceAll("(?i)<terrenos>", terrenos.substring(0, terrenos.length() - 2)));
					} else {
						p.sendMessage(getMensagem("sem_permissao"));
					}
				} else if (args.length == 2) {
					if (isAdmin(p)) {
						StringBuilder terrenos = new StringBuilder();
						if (getListaTerrenos(args[1]).isEmpty()) {
							p.sendMessage(getMensagem("jogador_nao_possui_terreno"));
							return true;
						}
						for (String s : getListaTerrenos(args[1])) {
							terrenos.append(s + ", ");
						}
						p.sendMessage(getMensagem("terrenos_de").replaceAll("(?i)<terrenos>", terrenos.substring(0, terrenos.length() - 2)).replaceAll("(?i)<player>", args[1]));
					} else {
						p.sendMessage(getMensagem("sem_permissao"));
					}
				}
			}
			
			if (args[0].equalsIgnoreCase("tp")) {
				if (isAuthorized(p, "tp")) {
					if (args.length == 1) {
						p.sendMessage(getMensagem("tp_usage"));
					} else if (args.length == 2) {
						if (getTerrenoLocation(p.getName(), args[1]) != null) {
							p.teleport(getTerrenoLocation(p.getName(), args[1]));
							p.sendMessage(getMensagem("teleportado_terreno").replaceAll("(?i)<terreno>", args[1]));
						} else {
							p.sendMessage(getMensagem("nao_possui_este_terreno"));
						}
					} else if (args.length == 3) {
						if (getTerrenoLocation(args[1], args[2]) != null) {
							p.teleport(getTerrenoLocation(args[1], args[2]));
							p.sendMessage(getMensagem("teleportado_terreno").replaceAll("(?i)<terreno>", args[2]));
						} else {
							p.sendMessage(getMensagem("jogador_nao_possui_este_terreno"));
						}
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			} 
			
			if (args[0].equalsIgnoreCase("admin")) {
				if (isAdmin(p)) {
					for (String s : FilesManager.getMensagensFile().getStringList("usage_admin")) {
						p.sendMessage(format(s));
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
			
			if (args[0].equalsIgnoreCase("addamigo")) {
				if (isAuthorized(p, "addamigo")) {
					if (args.length < 2 && isInYourRegion(p)) {
						p.sendMessage(getMensagem("addamigo_usage_2"));
						return true;
					} else if (args.length < 3 &&  !isInYourRegion(p)) {
						p.sendMessage(getMensagem("addamigo_usage"));
						return true;
					}
					if (isInYourRegion(p)) {
						addAmigo(p, getTerrenoNome(p), args[1]);
					} else {
						addAmigo(p, args[2], args[1]);
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
			
			if (args[0].equalsIgnoreCase("delamigo")) {
				if (isAuthorized(p, "delamigo")) {
					if (args.length < 2 && isInYourRegion(p)) {
						p.sendMessage(getMensagem("delamigo_usage_2"));
						return true;
					} else if (args.length < 3 &&  !isInYourRegion(p)) {
						p.sendMessage(getMensagem("delamigo_usage"));
						return true;
					}
					if (isInYourRegion(p)) {
						delAmigo(p, getTerrenoNome(p), args[1]);
					} else {
						if (!hasTerreno(p, args[2])) {
							p.sendMessage(getMensagem("nao_possui_este_terreno"));
						} else {
							delAmigo(p, args[2], args[1]);
						}
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}

			if (args[0].equalsIgnoreCase("vender")) {
				if (isAuthorized(p, "vender")) {
					if (!isInYourRegion(p)) {
						p.sendMessage(getMensagem("no_seu_terreno"));
						return true;
					}
					if (args.length < 2) {
						p.sendMessage(getMensagem("vender_usage"));
						return true;
					}
					try {
						venderTerreno(p, Integer.parseInt(args[1]));
					} catch (NumberFormatException e) {
						p.sendMessage(getMensagem("apenas_numeros"));
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
			
			if (args[0].equalsIgnoreCase("pvp")) {
				if (isAuthorized(p, "pvp")) {
					if (args.length < 2 && isInYourRegion(p)) {
						p.sendMessage(getMensagem("pvp_usage_2"));
						return true;
					} else if (args.length < 3 &&  !isInYourRegion(p)) {
						p.sendMessage(getMensagem("pvp_usage"));
						return true;
					}
					ProtectedRegion region = null;
					int preco = Main.getPlugin().getConfig().getInt("Custos.pvp");
					
					if (!Hooks.getEconomy().has(p.getName(), preco)) {
						p.sendMessage(getMensagem("sem_dinheiro").replaceAll("(?i)<money>", Integer.toString(preco)));
						return true;
					}
					
					try {
						switch (args[1].toLowerCase()) {
							case "ativar":
								if (isInYourRegion(p)) {
									region = getTerreno(p.getName(), getTerrenoNome(p));
									region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(Hooks.getWorldguard(), p, "allow"));
									p.sendMessage(getMensagem("pvp_ativado").replaceAll("(?i)<terreno>", getTerrenoNome(p)));
								} else {
									region = getTerreno(p.getName(), args[2]);
									region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(Hooks.getWorldguard(), p, "allow"));
									p.sendMessage(getMensagem("pvp_ativado").replaceAll("(?i)<terreno>", args[2]));
								}
								Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
								break;
							case "desativar":
								if (isInYourRegion(p)) {
									region = getTerreno(p.getName(), getTerrenoNome(p));
									region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(Hooks.getWorldguard(), p, "deny"));
									p.sendMessage(getMensagem("pvp_desativado").replaceAll("(?i)<terreno>", getTerrenoNome(p)));
								} else {
									region = getTerreno(p.getName(), args[2]);
									region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(Hooks.getWorldguard(), p, "deny"));
									p.sendMessage(getMensagem("pvp_desativado").replaceAll("(?i)<terreno>", args[2]));
								}
								Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
								break;
							default:
								if (isInYourRegion(p)) {
									p.sendMessage(getMensagem("pvp_usage_2"));
								} else {
									p.sendMessage(getMensagem("pvp_usage"));
								}
								break;
						}
					} catch (InvalidFlagFormat e) {
						p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
						logError("Ocorreu um erro ao desativar o pvp  do terreno. " + region.getId());
						e.printStackTrace();
					}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
				
			}
			
			if (args[0].equalsIgnoreCase("blockcmds")) {
				if (isAuthorized(p, "blockcmds")) {
					if (args.length < 3 && isInYourRegion(p)) {
						p.sendMessage(getMensagem("blockcmds_usage_2"));
						return true;
					} else if (args.length < 4 &&  !isInYourRegion(p)) {
						p.sendMessage(getMensagem("blockcmds_usage"));
						return true;
					}
					
					int preco = Main.getPlugin().getConfig().getInt("Custos.blockcmd");
					
					if (!Hooks.getEconomy().has(p.getName(), preco)) {
						p.sendMessage(getMensagem("sem_dinheiro").replaceAll("(?i)<money>", Integer.toString(preco)));
						return true;
					}
					
					
					switch (args[1].toLowerCase()) {// 3 terereno, 2 comando
						case "add":
							if (isInYourRegion(p)) {
								if (Main.getPlugin().getConfig().getBoolean("Limite_Comandos.Ativar") && getComandosBloqueados(p.getName(), getTerrenoNome(p)).size() >= Main.getPlugin().getConfig().getInt("Limite_Comandos.Limite") && !isAuthorized(p, "bypasslimite")) {
									p.sendMessage(getMensagem("nao_pode_bloquear"));
									return true;
								}
								if (getComandosBloqueados(p.getName(), getTerrenoNome(p)).contains(args[2].startsWith("/") ? args[2] : "/" + args[2])) {
									p.sendMessage(getMensagem("ja_bloqueado"));
									return true;
								}
								if (args[2].equalsIgnoreCase("/")) {
									if (isInYourRegion(p)) {
										p.sendMessage(getMensagem("blockcmds_usage_2"));
									} else {
										p.sendMessage(getMensagem("blockcmds_usage"));
									}
									return true;
								}
								if (Main.getPlugin().getConfig().getStringList("Comandos").contains(args[2].startsWith("/") ? args[2].substring(1) : args[2])) {
									p.sendMessage(getMensagem("bloquear_este_comando"));
									return true;
								}
								addComandoBloqueado(p.getName(), getTerrenoNome(p), args[2]);
							} else {
								if (Main.getPlugin().getConfig().getBoolean("Limite_Comandos.Ativar") && getComandosBloqueados(p.getName(), args[3]).size() <= Main.getPlugin().getConfig().getInt("Limite_Comandos.Limite") && !isAuthorized(p, "bypasslimite")) {
									p.sendMessage(getMensagem("nao_pode_bloquear"));
									return true;
								}
								if (getComandosBloqueados(p.getName(), args[3]).contains(args[2].startsWith("/") ? args[2] : "/" + args[2])) {
									p.sendMessage(getMensagem("ja_bloqueado"));
									return true;
								}
								if (!hasTerreno(p, args[3])) {
									p.sendMessage(getMensagem("nao_possui_este_terreno"));
									return true;
								}
								addComandoBloqueado(p.getName(), args[3], args[2]);
							}
							p.sendMessage(getMensagem("cmd_bloqueado").replaceAll("(?i)<terreno>", isInYourRegion(p) ? getTerrenoNome(p) : args[3]).replaceAll("(?i)<comando>", args[2].startsWith("/") ? args[2] : "/" + args[2]));
							Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
							break;
			
						case "remover":
							if (isInYourRegion(p)) {
								if (!getComandosBloqueados(p.getName(), getTerrenoNome(p)).contains(args[2].startsWith("/") ? args[2] : "/" + args[2])) {
									p.sendMessage(getMensagem("nao_bloqueado"));
									return true;
								}
								removeComandoBloqueado(p.getName(), getTerrenoNome(p), args[2]);
							} else {
								if (!getComandosBloqueados(p.getName(), args[3]).contains(args[2].startsWith("/") ? args[2] : "/" + args[2])) {
									p.sendMessage(getMensagem("nao_bloqueado"));
									return true;
								}
								if (!hasTerreno(p, args[3])) {
									p.sendMessage(getMensagem("nao_possui_este_terreno"));
									return true;
								}
								removeComandoBloqueado(p.getName(), args[3], args[2]);
							}
							Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
							p.sendMessage(getMensagem("cmd_desbloqueado").replaceAll("(?i)<terreno>", isInYourRegion(p) ? getTerrenoNome(p) : args[3]).replaceAll("(?i)<comando>", args[2].startsWith("/") ? args[2] : "/" + args[2]));
							break;
						default:
							if (isInYourRegion(p)) {
								p.sendMessage(getMensagem("blockcmds_usage_2"));
							} else {
								p.sendMessage(getMensagem("blockcmds_usage"));
							}
							break;
						}
				} else {
					p.sendMessage(getMensagem("sem_permissao"));
				}
			}
		}
		
		CommandSender p = sender;
			
		if (args[0].equalsIgnoreCase("deletar")) {
			if (isAuthorized(p, "deletar")) {
				if (args.length == 1) {
					p.sendMessage(getMensagem("deletar_usage"));
				} else if (args.length == 2) {
					if (getTerreno(p.getName(), args[1]) == null) {
						p.sendMessage(getMensagem("nao_possui_este_terreno"));
					} else if (deleteConfim.contains(p.getName())) {
						p.sendMessage(getMensagem("deletando"));
						deletarTerreno(p.getName(), args[1]);
						p.sendMessage(getMensagem("deletado").replaceAll("(?i)<terreno>", args[1]));
						deleteConfim.remove(p.getName());
					} else {
						p.sendMessage(getMensagem("deletar_confim").replaceAll("(?i)<terreno>", args[1]));
						deleteConfim.add(p.getName());
						final CommandSender pp = p;
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (deleteConfim.contains(pp.getName())) {
									deleteConfim.remove(pp.getName());
								}
								
							}
						}.runTaskLater(Main.getPlugin(), 400);
					}
				} else if (args.length == 3) {
					if (isAdmin(p)) {
						if (args[2].equalsIgnoreCase("todos")) {//0-cmd; 1-player; 2-args
							if (deleteConfim.contains(p.getName())) {
								p.sendMessage(getMensagem("deletando_2").replaceAll("(?i)<player>", args[1]));
								for (String terreno : getListaTerrenos(args[1])) {
									deletarTerreno(args[1], terreno);
								}
								p.sendMessage(getMensagem("deletado_2").replaceAll("(?i)<player>", args[1]));
								deleteConfim.remove(p.getName());
							}  else {
								p.sendMessage(getMensagem("deletar_confim_2").replaceAll("(?i)<player>", args[1]));
								deleteConfim.add(p.getName());
								final CommandSender pp = p;
								new BukkitRunnable() {
									
									@Override
									public void run() {
										if (deleteConfim.contains(pp.getName())) {
											deleteConfim.remove(pp.getName());
										}
										
									}
								}.runTaskLater(Main.getPlugin(), 400);
							}
						} else {
							if (getTerreno(args[1], args[2]) == null) {
								p.sendMessage(getMensagem("jogador_nao_possui_este_terreno"));
								return true;
							}
							if (deleteConfim.contains(p.getName())) {
								p.sendMessage(getMensagem("deletando"));
								deletarTerreno(args[1], args[2]);
								p.sendMessage(getMensagem("deletado").replaceAll("(?i)<terreno>", args[2]));
								deleteConfim.remove(p.getName());
							} else {
								p.sendMessage(getMensagem("deletar_confim").replaceAll("(?i)<terreno>", args[2]));
								deleteConfim.add(p.getName());
								final CommandSender pp = p;
								new BukkitRunnable() {
										
									@Override
									public void run() {
										if (deleteConfim.contains(pp.getName())) {
											deleteConfim.remove(pp.getName());
										}
											
									}
								}.runTaskLater(Main.getPlugin(), 400);
							}
						} 
					} else {
						p.sendMessage(getMensagem("sem_permissao"));
					}
				}
		} else {
			p.sendMessage(getMensagem("sem_permissao"));		
		}
	}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (isAdmin(p)) {
				Main.getPlugin().reloadConfig();
				p.sendMessage("§3[LsTerrenos] §bConfig recarregada.");
			}
		}
		return false;
	}
	

 
}
