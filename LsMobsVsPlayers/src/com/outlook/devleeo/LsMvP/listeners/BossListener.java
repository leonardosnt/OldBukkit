package com.outlook.devleeo.LsMvP.listeners;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.isAcontecendo;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.setGanhador;

import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.metodos.Metodos;

public class BossListener implements Listener {
	
	@EventHandler
	void onDamage(EntityDamageByEntityEvent e) {
		if (Metodos.boss == null) return;
		
		if (e.getEntity() == Metodos.boss) {
			((Giant)Metodos.boss).setCustomName(Main.getInstance().getConfig().getString("BossName").replace("&", "§").replace("{vida}", String.valueOf(((Giant) Metodos.boss).getHealth())).replace("<3", "♥").replace("{max}", String.valueOf(Main.getInstance().getConfig().getInt("BossHealth"))));
			e.setDamage((int) (e.getDamage() * 1.3));
		} 
	}
	
	@EventHandler
	void onBossDeath(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Giant && isAcontecendo()) {
			setGanhador(e.getEntity().getKiller());
		}
	}

}
