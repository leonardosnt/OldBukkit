package com.outlook.devleeo.LsMito.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.outlook.devleeo.LsMito.LsMitoPlugin;
import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;

public class PlayerListener implements Listener
{

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent e)
	{
		Player vic = e.getEntity();
		
		if (vic.getKiller() instanceof Player && LsMito.isMito(vic))
		{
			LsMito.setMito(vic.getKiller().getName());
		}
	}
	
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e)
	{
		final Player p = e.getPlayer();
		
		if (LsMito.isMito(p))
		{
			Bukkit.getScheduler().scheduleSyncDelayedTask(LsMitoPlugin.getPlugin(), new Runnable()
			{
				
				@Override
				public void run()
				{
					if (p.isOnline())
					{
						Bukkit.broadcastMessage(ConfigDefaults.mitoJoinedMessage.replaceAll("\\{player\\}", p.getName()));
					}
				}
			}, 20);
		}
	}
	
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent e)
	{
		if (LsMito.isMito(e.getPlayer()))
		{
			Bukkit.broadcastMessage(ConfigDefaults.mitoExitedMessage.replaceAll("\\{player\\}", e.getPlayer().getName()));
		}
	}
}
