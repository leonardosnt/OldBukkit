package com.outlook.devleeo.legendchatExt;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor {
	
	public static Set<Player> chatTell = new HashSet<>();
	public static Set<Player> chatGlobal = new HashSet<>();
	public static Set<Player> allChats = new HashSet<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player)sender;
			
			if (args.length == 0) {
				for (String s : Main.config.getStringList("Usage")) {
					p.sendMessage(s.replace("&", "§"));
				}
				return true;
			}
			
			if (args[0].equalsIgnoreCase("global")) {
				if (chatGlobal.contains(p)) {
					chatGlobal.remove(p);
					p.sendMessage(Main.config.getString("Chat_global_ativado").replace("&", "§"));
				} else {
					chatGlobal.add(p);
					p.sendMessage(Main.config.getString("Chat_global_desativado").replace("&", "§"));
				}
			}
			
			if (args[0].equalsIgnoreCase("tell")) {
				if (chatTell.contains(p)) {
					chatTell.remove(p);
					p.sendMessage(Main.config.getString("Chat_tell_ativado").replace("&", "§"));
				} else {
					chatTell.add(p);
					p.sendMessage(Main.config.getString("Chat_tell_desativado").replace("&", "§"));
				}
			}
			
			if (args[0].equalsIgnoreCase("*")) {
				if (allChats.contains(p)) {
					allChats.remove(p);
					p.sendMessage(Main.config.getString("Todos_chat_ativado").replace("&", "§"));
				} else {
					allChats.add(p);
					p.sendMessage(Main.config.getString("Todos_chat_desativado").replace("&", "§"));
				}
			}
			
		} else {
			sender.sendMessage("§cEste comando nao pode ser executado pelo console.");
		}
		return false;
	}

}
