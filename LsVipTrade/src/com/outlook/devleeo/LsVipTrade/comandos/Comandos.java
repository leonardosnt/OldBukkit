package com.outlook.devleeo.LsVipTrade.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsVipTrade.Main;
import com.outlook.devleeo.LsVipTrade.hooks.EconomyHook;
import com.outlook.devleeo.LsVipTrade.utils.Utils;

public class Comandos extends Utils implements CommandExecutor {
	
	public static List<Player> confirmar = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lsCommand, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando nao pode ser executado pelo console.");
			return true;
		}
		final Player p = (Player)sender;
		
		if (lsCommand.equalsIgnoreCase("venderVip")) {
			if (isAuthorized(p, "LsVipTrade.vendervip")) {
				if (!vipAVenda.isEmpty()) {
					p.sendMessage(getMensagem("Vip_Sendo_Vendido"));
					return true;
				}
				if (args.length < 2) {
					p.sendMessage(getMensagem("Usage_Vender"));
					return true;
				}
				if (!isKeyValida(args[0])) {
					p.sendMessage(getMensagem("Key_Invalida"));
					return true;
				}
				if (getPreco(args[1]) == -1) {
					for (String s : precoInvalido) {
						p.sendMessage(formatMessage(s));
					}
					return true;
				}
				if (!confirmar.contains(p)) {
					confirmar.add(p);
					p.sendMessage(getMensagem("Confirmar_Venda").replace("<preço>", args[1]));
					return true;
				}
				confirmar.clear();
				vipAVenda.put(p, args[0] + ":" + args[1]);
				
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						try {
							String[] keyAndPreco = vipAVenda.get(p).split(":");
							if (isKeyValida(keyAndPreco[0])) {
								for (String s : vendendoMensagens) {
									Bukkit.broadcastMessage(formatMessage(s).replace("<vendedor>", p.getName()).replace("<preço>", keyAndPreco[1]));
								}
							} else {
								cancelarVenda();
							}
						} catch (NullPointerException e) {
							finalizarVenda();
						}
					}
				}, 0, TEMPO_ENTRE_MSGS * 20);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!vipAVenda.isEmpty()) {
							cancelarVenda();
						}
					}
				}.runTaskLater(Main.getInstance(), TEMPO_MAX * 20);
			}
		}
		
		if (lsCommand.equalsIgnoreCase("cancelarVenda")) {
			if (isAuthorized(p, "LsVipTrade.cancelarvenda")) {
				if (vipAVenda.isEmpty() && confirmar.isEmpty()) {
					p.sendMessage(getMensagem("Nenhum_Vip"));
					return true;
				}
				if (vipAVenda.get(p) != null || confirmar.contains(p)) {
					cancelarVenda();
				} else {
					p.sendMessage(getMensagem("Nao_Vendendo"));
				}
			}
		}
		
		if (lsCommand.equalsIgnoreCase("comprarvip")) {
			if (isAuthorized(p, "LsVipTrade.comprarvip")) {
				if (args.length == 0) {
					p.sendMessage(getMensagem("Usage_Comprar"));
					return true;
				}
				if (vipAVenda.isEmpty()) {
					p.sendMessage(getMensagem("Nenhum_Vip"));
					return true;
				}
				if (vipAVenda.get(p) != null) {
					p.sendMessage(getMensagem("Comprar_Seu_Vip"));
					return true;
				}
				Player p2 = Bukkit.getPlayer(args[0]);
				
				if (vipAVenda.get(p2) == null) {
					p.sendMessage(getMensagem("Jogador_Nao_Vendendo"));
					return true;
				} else {
					String key = vipAVenda.get(p2).split(":")[0];
					int preco = getPreco(vipAVenda.get(p2).split(":")[1]);
					if (!isKeyValida(key)) {
						cancelarVenda();
						return true;
					}
					if (EconomyHook.eco.has(p.getName(), preco)) {
						EconomyHook.eco.withdrawPlayer(p.getName(), preco);
						EconomyHook.eco.depositPlayer(p2.getName(), preco);
						ativarVip(p, key);
						finalizarVenda();
						for (String s : vendaFinalizada) {
							Bukkit.broadcastMessage(formatMessage(s).replace("<player>", p.getName()));
						}
					} else {
						p.sendMessage(getMensagem("Dinheiro_Suficiente"));
					}
				}
			}
		}
		return false;
	}
}
