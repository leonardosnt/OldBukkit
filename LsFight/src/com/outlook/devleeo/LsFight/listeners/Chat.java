package com.outlook.devleeo.LsFight.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.outlook.devleeo.LsFight.LsFight;
import com.outlook.devleeo.LsFight.outros.Files;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Chat implements Listener {

	@EventHandler
	void onChat(ChatMessageEvent e) {
		if (LsFight.getInstance().getConfig().getBoolean("Premios.Tag.Ativar")) {
			if (e.getTags().contains("lsfight") && e.getSender().getName().equals(Files.getInstance().getDataFile().getString("Vencedor"))) {
				e.setTagValue("lsfight", LsFight.getInstance().getConfig().getString("Premios.Tag.Formato").replace("&", "§"));
			}
		}
	}
}
