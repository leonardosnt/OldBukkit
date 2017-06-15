package com.outlook.devleeo.LsMito.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;

public class VanillaChatListener implements Listener
{

	@EventHandler
	private void onChat(AsyncPlayerChatEvent e)
	{
		if (LsMito.isMito(e.getPlayer()))
		{
			e.setFormat("<" + e.getFormat().split("<")[0] + ConfigDefaults.prefixChat + e.getFormat().split("<")[1]);
		}
	}
}
