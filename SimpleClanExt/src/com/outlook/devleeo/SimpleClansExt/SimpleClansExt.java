package com.outlook.devleeo.SimpleClansExt;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleClansExt extends JavaPlugin implements Listener {
	
	public SimpleClans sc;

	@Override
	public void onEnable() {
		if (this.getServer().getPluginManager().getPlugin("SimpleClans") == null) {
			System.err.println("Simpleclans nao encontrado, desativando plugin.");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.sc = (SimpleClans) getServer().getPluginManager().getPlugin("SimpleClans");
		System.out.println("[SimpleClanExt] Plugin habilitado (Criador: DevLeeo)");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[SimpleClanExt] Plugin desabilitado (Criador: DevLeeo)");
	}
	
/*
  REVIEW 2016: Precisa adicionar permissao
  
  @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("resetally")) {
			for (Clan clan : sc.getClanManager().getClans()) {
				for (String ally: clan.getAllies()) {
					clan.removeAlly(sc.getClanManager().getClan(ally));
				}
			}
			
			sender.sendMessage("§aTodos as alianças de todos os clans foram desfeitas.");
		}
		return false;
	}
*/
	
	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/clan invite")) {
			if (this.sc.getClanManager().getClanPlayer(e.getPlayer()).isLeader() && this.sc.getClanManager().getClanByPlayerName(e.getPlayer().getName()).getMembers().size() >= Integer.parseInt(this.getConfig().getString("MEMBROS_POR_CLAN"))) {
				e.getPlayer().sendMessage(this.getConfig().getString("MENSAGEM_LIMITE").replace("&", "§"));
				e.setCancelled(true);
			}
		}
	}
}
