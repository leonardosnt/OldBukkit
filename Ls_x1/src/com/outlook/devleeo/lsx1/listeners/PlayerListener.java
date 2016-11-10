package com.outlook.devleeo.lsx1.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.outlook.devleeo.lsx1.Main;
import com.outlook.devleeo.lsx1.comandos.Comandos;
import com.outlook.devleeo.lsx1.metodos.Metodos;
import com.outlook.devleeo.lsx1.outros.Data;
import com.outlook.devleeo.lsx1.outros.Mensagens;

public class PlayerListener implements Listener {
	
	@EventHandler
	void onDeath(PlayerDeathEvent e) {
		if (Comandos.emx1.contains(e.getEntity().getPlayer())) {
			Comandos.emx1.remove(e.getEntity().getPlayer());
			if (Comandos.emx1.isEmpty()) {
				Metodos.teleportSaida(e.getEntity());
				return;
			}
			Mensagens.mensagemMorreu(Comandos.emx1.get(0), e.getEntity().getPlayer());
			Metodos.setVencedor(Comandos.emx1.get(0));
			Metodos.teleportSaida(e.getEntity().getPlayer());
		}
	}
	
	@EventHandler
	void onQuit(PlayerQuitEvent e) {
		if (Comandos.convite.contains(e.getPlayer())) {
			Bukkit.broadcastMessage(Mensagens.formatMessage(Data.getInstance().getMensagens().getString("Disconectou")).replace("{player}", e.getPlayer().getName()));
			Metodos.finalizarX1();
		}
		
		if (Comandos.emx1.contains(e.getPlayer())) {
			Comandos.emx1.remove(e.getPlayer());
			if (Comandos.emx1.isEmpty()) {
				Metodos.teleportSaida(e.getPlayer());
				return;
			}
			Mensagens.mensagemFugiu(Comandos.emx1.get(0), e.getPlayer());
			Metodos.setVencedor(Comandos.emx1.get(0));
			Metodos.teleportSaida(e.getPlayer());
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	void blockComands(PlayerCommandPreprocessEvent e) {
		if (Main.getInstance().getConfig().getBoolean("BloquearComandos") && Comandos.emx1.contains(e.getPlayer())) {
			for (String comando : Main.getInstance().getConfig().getStringList("ComandosLiberados")) {
				if (e.getMessage().startsWith(comando))return;
			}
			e.getPlayer().sendMessage(Mensagens.formatMessage(Data.getInstance().getMensagens().getString("ComandosBloqueados")));
			e.setCancelled(true);
		}
	}
}
