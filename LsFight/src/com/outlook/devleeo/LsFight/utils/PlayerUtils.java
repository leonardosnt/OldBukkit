package com.outlook.devleeo.LsFight.utils;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.outlook.devleeo.LsFight.outros.Hooks;

public class PlayerUtils {
	
	public static boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage("§5[LsFight] §cEste comando nao pode ser executado pelo console.");
			return false;
		}
	}
	
	public static boolean isAuthorized(CommandSender sender) {
		if (sender.hasPermission("lsfight.admin")) {
			return true;
		} else {
			sender.sendMessage(MensagensUtils.getMensagem("SEM_PERMISSAO"));
			return false;
		}
	}
	
	public static boolean isParticipando(Player p) {
		return FightUtils.participantes.contains(p);
	}
	
	public static boolean isLutando(Player p) {
		return FightUtils.lutadores.contains(p);
	}

	public static void ativarClanFF(Player p) {
		if (Hooks.sc != null) {
			if (Hooks.sc.getClanManager().getClanPlayer(p) != null) {
				Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(true);
			}
		}
	}
	
	public static void desativarClanFF(Player p) {
		if (Hooks.sc != null) {
			if (Hooks.sc.getClanManager().getClanPlayer(p) != null) {
				Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(false);
			}
		}
	}
	
	public static boolean isInventoryEmpty(Player p) {
		for (ItemStack i : p.getInventory().getContents()) {
			if (i != null) {
				if (i.getType() != Material.AIR) {
					return false;
				}
			}
		}
		for (ItemStack a : p.getInventory().getArmorContents()) {
			if (a.getType() != Material.AIR ) {
				return false;
			}
		}
		return true;
	}
}
