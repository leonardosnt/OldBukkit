package com.outlook.devleeo.LsMagnata.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class PlayerJoinListener implements Listener
{
 
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e)
	{
		if (MagnataManager.getCurrentMagnata().equalsIgnoreCase(e.getPlayer().getName()))
		{
			if (LsMagnata.getPlugin().getConfig().getBoolean("Messages.Join.Enable"))
			{
				Bukkit.broadcastMessage(LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Messages.Join.Message").replaceAll("(?i)<player>", e.getPlayer().getName())));
			}
		}
	}
}
