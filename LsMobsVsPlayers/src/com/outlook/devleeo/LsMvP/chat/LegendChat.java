package com.outlook.devleeo.LsMvP.chat;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.formatMessage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.files.Files;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class LegendChat implements Listener {

	@EventHandler
	void onChat(ChatMessageEvent e) {
		if (Main.getInstance().getConfig().getBoolean("Tag.Ativada")) {
			if (e.getTags().contains("lsmvp") && e.getSender().getName().equals(Files.getInstance().getData().getString("Vencedor"))) {
				e.setTagValue("lsmvp", formatMessage(Main.getInstance().getConfig().getString("Tag.Formato")));
			}
		}
	}
}
