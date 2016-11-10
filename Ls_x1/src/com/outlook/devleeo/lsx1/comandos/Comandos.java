package com.outlook.devleeo.lsx1.comandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.lsx1.Main;
import com.outlook.devleeo.lsx1.metodos.Metodos;
import com.outlook.devleeo.lsx1.outros.Data;
import com.outlook.devleeo.lsx1.outros.Hooks;
import com.outlook.devleeo.lsx1.outros.Mensagens;

public class Comandos implements CommandExecutor {
	
	public static ArrayList<Player> emx1 = new ArrayList<>();
	public static ArrayList<Player> convite = new ArrayList<>();

	Data data = Data.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String LsCommand, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando nao pode ser executado pelo console.");
			return true;
		}
		
		final Player p = (Player)sender;
		
		if (LsCommand.equalsIgnoreCase("x1")) {
			
			if (args.length == 0) {
				for (String s : data.getMensagens().getStringList("Usage")) {
					if (!p.hasPermission("lsx1.admin") && s.contains("set")) {
						return true;
					}
					p.sendMessage(Mensagens.formatMessage(s));
				}
				return true;
			}
				
			if (args[0].equalsIgnoreCase("desafiar")) {
				
				if (args.length == 1) {
					p.sendMessage(Mensagens.getPrefix() + " §cUse /x1 desafiar (Jogador)");
					return true;
				}
				
				if (convite.size() > 0 || emx1.size() > 0) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("EmAndamento")));
					return true;
				}
				
				final Player p2 = Bukkit.getPlayer(args[1]);
				
				if (p2 == null) {
					p.sendMessage(Mensagens.getPrefix() +  " §cErro: Jogador não encontrado.");
					return true;
				}
					
				if (p2.getName() == p.getName()) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafiarASiMesmo")));
					return true;
				}
				
				if (!Hooks.eco.has(p.getName(), Main.getInstance().getConfig().getInt("Custo"))) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("SemDinheiro")));
					return true;
				} else if (!Hooks.eco.has(p2.getName(), Main.getInstance().getConfig().getInt("Custo"))) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("SemDinheiro2").replace("{desafiado}", p2.getName())));
					return true;
				}
				
				convite.add(p);
				convite.add(p2);
				Mensagens.mensagemDesafiado(p, p2);
				p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafioEnviado")).replace("{desafiado}", p2.getName()));
				Mensagens.mensagemVoceFoiDesafiado(p, p2);
					
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						convite.clear();
						Mensagens.mensagemPreferiuIgnorar(p, p2);
						
					}
				}.runTaskLater(Main.getInstance(), Main.getInstance().getConfig().getInt("ConviteTempo") * 20);
					
			}
				
			if (args[0].equalsIgnoreCase("setpos1")) {
				if (p.hasPermission("lsx1.admin")) {
					
					data.getData().set("Pos1.world", p.getLocation().getWorld().getName());
					data.getData().set("Pos1.x", p.getLocation().getX());
					data.getData().set("Pos1.y", p.getLocation().getY());
					data.getData().set("Pos1.z", p.getLocation().getZ());
					data.getData().set("Pos1.yaw", p.getLocation().getYaw());
					data.getData().set("Pos1.pitch", p.getLocation().getPitch());
					data.saveData();
			           
					p.sendMessage(Mensagens.getPrefix() + " §ePosição 1 setada com sucesso.");
				} else {
					Mensagens.semPermMensagem(p);
				}
			}
				
			if (args[0].equalsIgnoreCase("setpos2")) {
				if (p.hasPermission("lsx1.admin")) {
					
					data.getData().set("Pos2.world", p.getLocation().getWorld().getName());
					data.getData().set("Pos2.x", p.getLocation().getX());
					data.getData().set("Pos2.y", p.getLocation().getY());
					data.getData().set("Pos2.z", p.getLocation().getZ());
					data.getData().set("Pos2.yaw", p.getLocation().getYaw());
					data.getData().set("Pos2.pitch", p.getLocation().getPitch());
					data.saveData();
			           
					p.sendMessage(Mensagens.getPrefix() + " §ePosição 2 setada com sucesso.");
				} else {
					Mensagens.semPermMensagem(p);
				}
			}
			
			if (args[0].equalsIgnoreCase("setsaida")) {
				if (p.hasPermission("lsx1.admin")) {
					
					data.getData().set("Saida.world", p.getLocation().getWorld().getName());
					data.getData().set("Saida.x", p.getLocation().getX());
					data.getData().set("Saida.y", p.getLocation().getY());
					data.getData().set("Saida.z", p.getLocation().getZ());
					data.getData().set("Saida.yaw", p.getLocation().getYaw());
					data.getData().set("Saida.pitch", p.getLocation().getPitch());
					data.saveData();
			           
					p.sendMessage(Mensagens.getPrefix() + " §eSaida setada com sucesso.");
				} else {
					Mensagens.semPermMensagem(p);
				}
			}
			
			if (args[0].equalsIgnoreCase("cancelar")) {
				if (p.hasPermission("lsx1.admin")) {
					if (!emx1.isEmpty()) {
						Bukkit.broadcastMessage(Mensagens.formatMessage(data.getMensagens().getString("FinalizadoPorAdm")));
						for(Player pp : emx1) {
							Metodos.teleportSaida(pp);
							Hooks.eco.depositPlayer(pp.getName(), Main.getInstance().getConfig().getInt("Custo"));
						}
						Metodos.finalizarX1();
					} else {
						p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("NenhumAcontecendo")));
					}
				} else {
					Mensagens.semPermMensagem(p);
				}
			}
			
			if (args[0].equalsIgnoreCase("setcamarote")) {
				if (p.hasPermission("lsx1.admin")) {
					
					data.getData().set("Camarote.world", p.getLocation().getWorld().getName());
					data.getData().set("Camarote.x", p.getLocation().getX());
					data.getData().set("Camarote.y", p.getLocation().getY());
					data.getData().set("Camarote.z", p.getLocation().getZ());
					data.getData().set("Camarote.yaw", p.getLocation().getYaw());
					data.getData().set("Camarote.pitch", p.getLocation().getPitch());
					data.saveData();
			           
					p.sendMessage(Mensagens.getPrefix() + " §eCamarote setado com sucesso.");
				} else {
					Mensagens.semPermMensagem(p);
				}
			}
			
			if (args[0].equalsIgnoreCase("aceitar")) {
				if (!convite.isEmpty()) {
					if (convite.get(1).equals(p)) {
						if (!Hooks.eco.has(p.getName(), Main.getInstance().getConfig().getInt("Custo")) || !Hooks.eco.has(convite.get(0).getName(), Main.getInstance().getConfig().getInt("Custo"))) {
							p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("cancelado")));
							convite.get(0).sendMessage(Mensagens.formatMessage(data.getMensagens().getString("cancelado")));
							Metodos.finalizarX1();
							return true;
						}
						emx1.add(p);
						emx1.add(convite.get(0));
						Hooks.eco.withdrawPlayer(p.getName(), Main.getInstance().getConfig().getInt("Custo"));
						Hooks.eco.withdrawPlayer(convite.get(0).getName(), Main.getInstance().getConfig().getInt("Custo"));
						Metodos.teleportX1(p, convite.get(0));
						convite.clear();
						Bukkit.getScheduler().cancelTasks(Main.getInstance());
						
						Mensagens.mensagemAceitou(p);
						
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Bukkit.broadcastMessage(Mensagens.formatMessage(data.getMensagens().getString("x1Empate")));
								for(Player pp : emx1) {
									Metodos.teleportSaida(pp);
									Hooks.eco.depositPlayer(pp.getName(), Main.getInstance().getConfig().getInt("Custo"));
								}
								Metodos.finalizarX1();
							}
						}.runTaskLater(Main.getInstance(), Main.getInstance().getConfig().getInt("TempoMaximo") * 60 * 20);
						
					} else {
						p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafioPendente")));
					}
				} else {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafioPendente")));
				}
			}
			
			if (args[0].equalsIgnoreCase("recusar")) {
				if (!convite.isEmpty()) {
					if (convite.get(1).equals(p)) {
						
						Mensagens.mensagemRecusouDesafio(convite.get(0), p);
						convite.clear();
						Bukkit.getScheduler().cancelTasks(Main.getInstance());
						
					} else {
						p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafioPendente")));
					}
				} else {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("DesafioPendente")));
				}
			}
			
			if (args[0].equalsIgnoreCase("camarote")) {
				if (Metodos.getCamaroteLocation() == null) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("CamaroteNaoDefinido")));
					return true;
				}
				if (emx1.size() <= 0) {
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("NenhumAcontecendo")));
					return true;
				} else {
					p.teleport(Metodos.getCamaroteLocation());
					p.sendMessage(Mensagens.formatMessage(data.getMensagens().getString("TeleportadoCamarote")));
				}
					
			}
				
		}
		return false;
	}
}
