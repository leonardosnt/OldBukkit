package com.outlook.devleeo.LsTerrenos.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.outlook.devleeo.LsTerrenos.commom.Hooks;
import com.outlook.devleeo.LsTerrenos.commom.Utils;
import com.outlook.devleeo.LsTerrenos.managers.TerrenosManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlayerListener implements Listener {

	@EventHandler
	void safeNick(PlayerLoginEvent e) {
		if (e.getPlayer().getName().contains("-")) {
			e.disallow(Result.KICK_OTHER, "§cSeu nickname não pode conter um traço. \" - \"");
		}
	}
	
	@EventHandler
	void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			if (b.getType() == Material.SIGN_POST) {
				Sign s = (Sign) b.getState();
				if (s.getLine(0).equals(Utils.getMensagem("placa_venda.linha1").replace("&", ""))) {
					int i = -1;
					if (Utils.getMensagem("placa_venda.linha1").contains("<preco>")) {
						i = 0;
					} else if (Utils.getMensagem("placa_venda.linha2").contains("<preco>")) {
						i = 1;
					} else if (Utils.getMensagem("placa_venda.linha3").contains("<preco>")) {
						i = 2;
					} else if (Utils.getMensagem("placa_venda.linha4").contains("<preco>")) {
						i = 3;
					}
					
					try {
						int preco = Integer.parseInt(s.getLine(i).replaceAll("§[a-zA-Z0-9]", "").replaceAll("\\D", ""));
						Player p = e.getPlayer();
						ProtectedRegion region = Hooks.getWorldguard().getRegionManager(p.getWorld()).getRegion(TerrenosManager.getTerrenoIdByBlock(b));
						
						if (p.getName().equalsIgnoreCase(region.getId().split("-")[0])) {
							p.sendMessage(Utils.getMensagem("comprar_seu_terreno"));
							return;
						}
						
						if (Hooks.getEconomy().has(p.getName(), preco)) {
							TerrenosManager.comprarTerreno(p, region.getId(), region.getId().split("-")[0], preco);
							b.setType(Material.AIR);
						} else {
							p.sendMessage(Utils.getMensagem("sem_dinheiro").replaceAll("(?i)<money>", Integer.toString(preco)));
						}
					} catch (Exception e2) {
						e.getPlayer().sendMessage("§cOcorreu um erro interno, contate um administrador.");
						Utils.logError("Ocorreu um erro, Erro: ");
						e2.printStackTrace();
					}
				}
			}
		}
	}
	
	@EventHandler
	void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SIGN_POST) {
			Sign s = (Sign) e.getBlock().getState();
			if (TerrenosManager.getTerrenoIdByBlock(e.getBlock()).startsWith(e.getPlayer().getName().toLowerCase() + "-") && s.getLine(0).equals(Utils.getMensagem("placa_venda.linha1"))) {
				e.getPlayer().sendMessage(Utils.getMensagem("venda_cancelada"));
			}	
		}
	}
	
	@EventHandler
	void onSign(SignChangeEvent e) {
		if (e.getLine(0).replaceAll("[§|&][a-zA-Z1-9]", "").equalsIgnoreCase(Utils.getMensagem("placa_venda.linha1").replaceAll("[§|&][a-zA-Z1-9]", ""))) {
			e.getPlayer().sendMessage(Utils.getMensagem("erro_placa"));
			e.getBlock().breakNaturally();
		}
	}
	
	@EventHandler
	void onCommandProcess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		ProtectedRegion region = Hooks.getWorldguard().getRegionManager(p.getWorld()).getRegion(TerrenosManager.getTerrenoIdByBlock(e.getPlayer().getLocation().getBlock()));
		if (region == null || !region.getId().contains("-")) return;
		String[] s = region.getId().split("-");
		
		if (!region.isOwner(e.getPlayer().getName()) && !region.isMember(e.getPlayer().getName())) {
			for (String cmd : Utils.getComandosBloqueados(s[0], s[1])) {
				if (e.getMessage().startsWith(cmd)) {
					p.sendMessage(Utils.getMensagem("comando_bloqueado"));
					e.setCancelled(true);
					break;
				}
			}
		}
	}
}
