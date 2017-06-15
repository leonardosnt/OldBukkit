package com.outlook.devleeo.LsMagnata.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class PlayerQuitListener implements Listener
{
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent e)
	{
		if (MagnataManager.getCurrentMagnata().equalsIgnoreCase(e.getPlayer().getName()))
		{
			if (LsMagnata.getPlugin().getConfig().getBoolean("Messages.Leave.Enable"))
			{
				Bukkit.broadcastMessage(LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Messages.Leave.Message")).replaceAll("(?i)<player>", e.getPlayer().getName()));
			}
		}
	}
}
