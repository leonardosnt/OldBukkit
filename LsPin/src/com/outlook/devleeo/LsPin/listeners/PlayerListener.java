package com.outlook.devleeo.LsPin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsPin.LsPin;
import com.outlook.devleeo.LsPin.filesmanager.Config;
import com.outlook.devleeo.LsPin.pinManager.Pin;

public class PlayerListener implements Listener {
	
	@EventHandler
	void onJoin(final PlayerJoinEvent e) {
		if (Config.ATIVAR_MENSAGEM_JOIN && e.getPlayer().hasPlayedBefore() && !Pin.hasPin(e.getPlayer().getName())) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					e.getPlayer().sendMessage(Config.getMensagem("MENSAGEM_JOIN"));
				}
				
			}.runTaskLater(LsPin.getInstance(), 2 * 20);
		}
	}

}
