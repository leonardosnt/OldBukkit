package com.outlook.devleeo.LsMagnata.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class VanillaChatListener implements Listener
{

	@EventHandler
	private void onChat(AsyncPlayerChatEvent e)
	{
		if (e.getPlayer().getName().equalsIgnoreCase(MagnataManager.getCurrentMagnata()))
		{
			e.setFormat("<" + e.getFormat().split("<")[0] + LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Settings.ChatPrefix")) + e.getFormat().split("<")[1]);
		}
	}
}
