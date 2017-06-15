package com.outlook.devleeo.LsMito.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class LegendChatListener implements Listener
{

	@EventHandler
	private void onChat(ChatMessageEvent e)
	{
		if (e.getTags().contains("lsmito") &&  LsMito.isMito(e.getSender()))
		{
			e.setTagValue("lsmito", ConfigDefaults.prefixChat);
		}
	}
}
