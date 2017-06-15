package com.outlook.devleeo.LsMagnata.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class LegendChatListener implements Listener
{

	@EventHandler
	private void onChat(ChatMessageEvent e)
	{
		String currentMagnata = MagnataManager.getCurrentMagnata();
		if (e.getTags().contains("lsmagnata") &&  e.getSender().getName().equalsIgnoreCase(currentMagnata))
		{
			e.setTagValue("lsmagnata", LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Settings.ChatPrefix")));
		}
	}
}
