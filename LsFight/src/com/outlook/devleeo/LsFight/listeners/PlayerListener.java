package com.outlook.devleeo.LsFight.listeners;

import static com.outlook.devleeo.LsFight.utils.FightUtils.lutadores;
import static com.outlook.devleeo.LsFight.utils.FightUtils.participantes;
import static com.outlook.devleeo.LsFight.utils.FightUtils.venceuDuelo;
import static com.outlook.devleeo.LsFight.utils.PlayerUtils.isLutando;
import static com.outlook.devleeo.LsFight.utils.PlayerUtils.isParticipando;
import static com.outlook.devleeo.LsFight.utils.TeleportUtils.teleportSaida;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	void onDeath(PlayerDeathEvent e) {
		if (isLutando(e.getEntity().getPlayer()) && e.getEntity().getPlayer() == lutadores.get(0)) {
			participantes.remove(e.getEntity().getPlayer());
			teleportSaida(e.getEntity().getPlayer());
			venceuDuelo(lutadores.get(1));
			e.getDrops().clear();
		} else if (isLutando(e.getEntity().getPlayer()) && e.getEntity().getPlayer() == lutadores.get(1)) {
			participantes.remove(e.getEntity().getPlayer());
			teleportSaida(e.getEntity().getPlayer());
			venceuDuelo(lutadores.get(0));
			e.getDrops().clear();
		}
		
		if (isParticipando(e.getEntity().getPlayer())) {
			participantes.remove(e.getEntity().getPlayer());
			teleportSaida(e.getEntity().getPlayer());
		}
		
	}
	
	@EventHandler
	void onQuit(PlayerQuitEvent e) {
		if (isLutando(e.getPlayer()) && e.getPlayer() == lutadores.get(0)) {
			participantes.remove(e.getPlayer());
			teleportSaida(e.getPlayer());
			venceuDuelo(lutadores.get(1));
		} else if (isLutando(e.getPlayer()) && e.getPlayer() == lutadores.get(1)) {
			participantes.remove(e.getPlayer());
			teleportSaida(e.getPlayer());
			venceuDuelo(lutadores.get(0));
		}
		
		if (isParticipando(e.getPlayer())) {
			participantes.remove(e.getPlayer());
			teleportSaida(e.getPlayer());
		}
	}
	
	@EventHandler
	void antiPvP(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if ((isLutando((Player)e.getDamager()) || isLutando((Player)e.getEntity())) || (isLutando((Player)e.getDamager()) && isLutando((Player)e.getEntity()))) {
				return;
			}
			if (isParticipando((Player)e.getEntity()) || isParticipando((Player)e.getDamager())){
				e.setCancelled(true);
			}
		} else if (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player && e.getEntity() instanceof Player){
			Projectile pj = (Projectile)e.getDamager();
			if (isParticipando((Player)pj.getShooter()) || isParticipando((Player)e.getEntity())){
				e.setCancelled(true);
			}
		}
	}
}
