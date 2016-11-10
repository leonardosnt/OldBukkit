package com.outlook.devleeo.lsx1.metodos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.lsx1.Main;
import com.outlook.devleeo.lsx1.comandos.Comandos;
import com.outlook.devleeo.lsx1.outros.Data;
import com.outlook.devleeo.lsx1.outros.Hooks;
import com.outlook.devleeo.lsx1.outros.Mensagens;

public class Metodos {
	
	static Data data = Data.getInstance();
	
	public static void desativarClanFF(Player p) {
		if (Bukkit.getPluginManager().getPlugin("SimpleClans") != null) {
			if (Hooks.sc.getClanManager().getClanPlayer(p) != null) {
				Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(false);
			}
		}
	}
	
	public static void ativarClanFF(Player p){
		if (Bukkit.getPluginManager().getPlugin("SimpleClans") != null) {
			if (Hooks.sc.getClanManager().getClanPlayer(p) != null) {
				Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(true);
			}
		}
	}
	
	public static void teleportSaida(Player p){
		World w = Bukkit.getWorld(data.getData().getString("Saida.world"));
		double x = data.getData().getDouble("Saida.x");
		double y = data.getData().getDouble("Saida.y");
		double z = data.getData().getDouble("Saida.z");
		float yaw = (float) data.getData().getDouble("Saida.yaw");
		float pitch = (float) data.getData().getDouble("Saida.pitch");
		
		Location saida = new Location(w, x, y, z, yaw, pitch);
		
		p.teleport(saida);
		desativarClanFF(p);
	}
	
	public static void teleportX1(Player p1, Player p2) {
		
		World w1 = Bukkit.getWorld(data.getData().getString("Pos1.world"));
		double x1 = data.getData().getDouble("Pos1.x");
		double y1 = data.getData().getDouble("Pos1.y");
		double z1 = data.getData().getDouble("Pos1.z");
		float yaw1 = (float) data.getData().getDouble("Pos1.yaw");
		float pitch1 = (float) data.getData().getDouble("Pos1.pitch");
		
		
		World w2 = Bukkit.getWorld(data.getData().getString("Pos2.world"));
		double x2 = data.getData().getDouble("Pos2.x");
		double y2 = data.getData().getDouble("Pos2.y");
		double z2 = data.getData().getDouble("Pos2.z");
		float yaw2 = (float) data.getData().getDouble("Pos2.yaw");
		float pitch2 = (float) data.getData().getDouble("Pos2.pitch");
		
		Location pos2 = new Location(w2, x2, y2, z2, yaw2, pitch2);
		Location pos1 = new Location(w1, x1, y1, z1, yaw1, pitch1);
		
		p1.teleport(pos1);
		p2.teleport(pos2);
		
		ativarClanFF(p1);
		ativarClanFF(p2);
		
	}
	
	public static void finalizarX1() {
		Comandos.emx1.clear();
		Comandos.convite.clear();
		Bukkit.getScheduler().cancelTasks(Main.getInstance());
	}
	
	public static void setVencedor(final Player p) {
		Hooks.eco.depositPlayer(p.getName(), Main.getInstance().getConfig().getInt("Premio"));
		p.sendMessage(Mensagens.formatMessage(Data.getInstance().getMensagens().getString("RecolherItens")));
		new BukkitRunnable() {
			
			public void run() {
				if (!Comandos.emx1.isEmpty()) {
					teleportSaida(p);
				}
				finalizarX1();
			}
		}.runTaskLater(Main.getInstance(), Main.getInstance().getConfig().getInt("TempoRecolherItems") * 20);
	}
	
	public static Location getCamaroteLocation() {
		if (!data.getData().contains("Camarote.")) {
			return null;
		}
		World w = Bukkit.getWorld(data.getData().getString("Camarote.world"));
		double x = data.getData().getDouble("Camarote.x");
		double y = data.getData().getDouble("Camarote.y");
		double z = data.getData().getDouble("Camarote.z");
		float yaw = (float) data.getData().getDouble("Camarote.yaw");
		float pitch = (float) data.getData().getDouble("Camarote.pitch");
		
		Location camarote = new Location(w, x, y, z, yaw, pitch);
		
		return camarote;
	}
}
