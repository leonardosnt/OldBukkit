package com.outlook.devleeo.com.lsdestemido;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Destemido implements Listener, CommandExecutor {
	
	public static Player destemido;
	
	public void setDestemido(Player p){
		Main.eco.depositPlayer(p.getName(), Main.getInstance().getConfig().getInt("Premio"));
		destemido = p;
	}
	
	@EventHandler
	void onEntityDeath(EntityDeathEvent e){
		Entity entity = e.getEntity();
		if(e.getEntity().getKiller() instanceof Player && entity instanceof EnderDragon){
			for(String msg : Main.getInstance().getConfig().getStringList("NovoDestemido")){
				msg = msg.replace("&", "§").replace("{player}", e.getEntity().getKiller().getName());
				Bukkit.broadcastMessage(msg);
			}
			setDestemido(e.getEntity().getKiller());
		}
	}
	
	@EventHandler
	void onChat(ChatMessageEvent e){
		if(destemido != null){
			if(e.getTags().contains("lsdestemido") && e.getSender().getName().equalsIgnoreCase(destemido.getName())){
				e.setTagValue("lsdestemido", Main.getInstance().getConfig().getString("Tag"));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String LsCommand, String[] args){
		if(LsCommand.equalsIgnoreCase("destemido")){
			if(!sender.hasPermission("lsdestemido.destemido")){
				sender.sendMessage("§4Você não tem acesso a este comando.");
				return true;
			}
		
			if(destemido == null){
				sender.sendMessage(Main.getInstance().getConfig().getString("Nenhum").replace("&", "§"));
				return true;
			}
			sender.sendMessage(Main.getInstance().getConfig().getString("DestemidoAtual").replace("&", "§").replace("{player}", destemido.getName()));
		}
		return false;
	}
	
}
