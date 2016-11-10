package com.outlook.devleeo.LsMvP.chat;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.formatMessage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.files.Files;

public class DefaultChat implements Listener {

	@EventHandler
	void onChat(AsyncPlayerChatEvent e) {
		if (e.getPlayer().getName().equals(Files.getInstance().getData().getString("Vencedor"))) {
			e.setFormat("<" + e.getFormat().split("<")[0] + formatMessage(Main.getInstance().getConfig().getString("Tag.Formato")) + e.getFormat().split("<")[1]);
		}
	}
}
