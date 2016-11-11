package com.outlook.devleeo.legendchatExt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import br.com.devpaulo.legendchat.api.events.PrivateMessageEvent;

public class Listeners implements Listener {

	/* Update 2016 - Fix Memory Leak (Não testado) */
	@EventHandler
	void onPlayerDisconnect(PlayerQuitEvent e) {
		Comandos.chatGlobal.remove(e);
		Comandos.chatTell.remove(e);
		Comandos.allChats.remove(e);
	}
	/* End Update 2016 */
	
	@EventHandler
	void onGlobalMessage(ChatMessageEvent e) {
		if (e.getChannel().getName().equalsIgnoreCase(Main.config.getString("Canal_Global"))) {
			if (Comandos.chatGlobal.contains(e.getSender())) {
				Comandos.chatGlobal.remove(e.getSender());
				e.getSender().sendMessage(Main.config.getString("Chat_global_ativado").replace("&", "§"));
			}
			e.getRecipients().removeAll(Comandos.chatGlobal);
		}
	}
	
	@EventHandler
	void onChatMessage(ChatMessageEvent e) {
		if (Comandos.allChats.contains(e.getSender())) {
			Comandos.allChats.remove(e.getSender());
			e.getSender().sendMessage(Main.config.getString("Todos_chat_ativado").replace("&", "§"));
		}
		e.getRecipients().removeAll(Comandos.allChats);
	}
	
	@EventHandler
	void onPrivateMessage(PrivateMessageEvent e) {
		if (Comandos.chatTell.contains(e.getSender())) {
			Comandos.chatTell.remove(e.getSender());
			e.getSender().sendMessage(Main.config.getString("Chat_tell_ativado").replace("&", "§"));
		}
		if (Comandos.chatTell.contains(e.getReceiver())) {
			e.getSender().sendMessage(Main.config.getString("Tell_Desativado").replace("&", "§"));
			e.setCancelled(true);
		}
	}
}
