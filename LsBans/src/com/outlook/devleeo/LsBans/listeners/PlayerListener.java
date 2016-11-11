package com.outlook.devleeo.LsBans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.outlook.devleeo.LsBans.managers.BanManager;
import com.outlook.devleeo.LsBans.managers.MensagensManager;
import com.outlook.devleeo.LsBans.outros.IpHistory;
import com.outlook.devleeo.LsBans.outros.Utils;

public class PlayerListener implements Listener {

	@EventHandler
	void ipHistory(PlayerJoinEvent e) {
		if (IpHistory.getIp(e.getPlayer().getName()) == null) {
			IpHistory.saveIp(e.getPlayer());
		} else if (!IpHistory.getIp(e.getPlayer().getName()).equals(e.getPlayer().getAddress().getHostName().toString())) {
			IpHistory.updateIp(e.getPlayer());
		}
	}
	
	@EventHandler
	void onJoin(PlayerLoginEvent e) {
		if (BanManager.isBanido(e.getPlayer().getName())) {
			String[] ban = BanManager.getBan(e.getPlayer().getName()).split(";");
			String motivo = ban[2], admin = ban[3];
			int tempo = Integer.parseInt(ban[1]);
			if (tempo == 0) {
				e.disallow(Result.KICK_OTHER, MensagensManager.getMensagem("Ban.Join.Permanente").replace("<admin>", admin).replace("<motivo>", motivo));
				return;
			} 
			if (Utils.getTempoRestante(e.getPlayer().getName()) >= tempo) {
				BanManager.unbanPlayer(e.getPlayer().getName());
				return;
			}
			long tempoRestante = (tempo - Utils.getTempoRestante(e.getPlayer().getName()));
			e.disallow(Result.KICK_OTHER, MensagensManager.getMensagem("Ban.Join.Temporario").replace("<tempo>", Long.toString(tempoRestante)).replace("<admin>", admin).replace("<motivo>", motivo));
		} else if (BanManager.isIpBanido(e.getAddress().getHostName())) {
			e.disallow(Result.KICK_OTHER, MensagensManager.getMensagem("Ban.Join.Ip_Banido"));
		}
	}
	
}
