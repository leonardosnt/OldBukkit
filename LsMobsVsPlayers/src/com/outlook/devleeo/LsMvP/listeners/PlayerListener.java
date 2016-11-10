package com.outlook.devleeo.LsMvP.listeners;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.formatMessage;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.teleportSaida;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.files.Files;
import com.outlook.devleeo.LsMvP.metodos.Metodos;

public class PlayerListener implements Listener {
	
	Files files = Files.getInstance();

	@EventHandler
	void antiPvP(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if (Metodos.participantes.contains(e.getDamager()) && Metodos.participantes.contains(e.getEntity())){
				e.setCancelled(true);
			}
		} else if (e.getDamager() instanceof Projectile){
			Projectile pj = (Projectile)e.getDamager();
			if (Metodos.participantes.contains(pj.getShooter()) && Metodos.participantes.contains(e.getEntity())){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	void onQuit(PlayerQuitEvent e) {
		if (Metodos.participantes.contains(e.getPlayer())) {
			teleportSaida(e.getPlayer());
			Metodos.participantes.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	void onDeath(PlayerDeathEvent e) {
		if (Metodos.participantes.contains(e.getEntity().getPlayer())) {
			Metodos.participantes.remove(e.getEntity().getPlayer());
			
			if (Main.getInstance().getConfig().getBoolean("MensagemMorte.Ativar")) {
				Bukkit.broadcastMessage(formatMessage(Main.getInstance().getConfig().getString("MensagemMorte.Mensagem").replace("{player}", e.getEntity().getPlayer().getName())));	
			}
			
		}
	}
	
	@EventHandler
	void onTeleport(final PlayerTeleportEvent e) {
		if (Metodos.participantes.contains(e.getPlayer()) && e.getCause() == TeleportCause.PLUGIN) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					Metodos.participantes.remove(e.getPlayer());
				}
			}.runTaskLater(Main.getInstance(), 20);
		}
	}
	
}
