package com.outlook.devleeo.LsVipTrade.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.outlook.devleeo.LsVipTrade.utils.Utils;

public class PlayerListener extends Utils implements Listener {
	
	@EventHandler
	void onQuit(PlayerQuitEvent e) {
		if (vipAVenda.containsKey(e.getPlayer())) {
			cancelarVenda();
		}
	}
	
}
