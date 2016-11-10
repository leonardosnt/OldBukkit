package com.outlook.devleeo.LsFight.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsFight.outros.Files;

public class TeleportUtils {
	
	static FileConfiguration data = Files.getInstance().getDataFile();
	
	public static void teleportEntrada(Player p) {
		
		if (data.getString("Entrada.") == null) {
			p.sendMessage(MensagensUtils.getPrefix() + " §cErro: Entrada do evento ainda não foi definida.");
			return;
		}
		
		World w = Bukkit.getWorld(data.getString("Entrada.World"));
		double x = data.getDouble("Entrada.X");
		double y = data.getDouble("Entrada.Y");
		double z = data.getDouble("Entrada.Z");
		float yaw = (float) data.getDouble("Entrada.Yaw");
		float pitch = (float) data.getDouble("Entrada.Pitch");
		
		Location entrada = new Location(w, x, y, z);
		entrada.setPitch(pitch);
		entrada.setYaw(yaw);
		
		p.teleport(entrada);
		PlayerUtils.ativarClanFF(p);
	}
	
	public static void teleportSaida(Player p) {
		
		if (data.getString("Saida.") == null) {
			p.sendMessage(MensagensUtils.getPrefix() + " §cErro: Saida do evento ainda não foi definida.");
			return;
		}
		
		World w = Bukkit.getWorld(data.getString("Saida.World"));
		double x = data.getDouble("Saida.X");
		double y = data.getDouble("Saida.Y");
		double z = data.getDouble("Saida.Z");
		float yaw = (float) data.getDouble("Saida.Yaw");
		float pitch = (float) data.getDouble("Saida.Pitch");
		
		Location saida = new Location(w, x, y, z);
		saida.setPitch(pitch);
		saida.setYaw(yaw);
		
		p.teleport(saida);
		PlayerUtils.desativarClanFF(p);
	}
	
	public static void teleportLutadores(Player p1, Player p2) {
		
		if (data.getString("Pos1.") == null || data.getString("Pos2.") == null) {
			Bukkit.broadcastMessage(MensagensUtils.getPrefix() + " §cErro: Posição 1 ou 2 do evento ainda não foi definida.");
			return;
		}
		
		World w1 = Bukkit.getWorld(data.getString("Pos1.World"));
		double x1 = data.getDouble("Pos1.X");
		double y1 = data.getDouble("Pos1.Y");
		double z1 = data.getDouble("Pos1.Z");
		float yaw1 = (float) data.getDouble("Pos1.Yaw");
		float pitch1 = (float) data.getDouble("Pos1.Pitch");
		
		Location pos1 = new Location(w1, x1, y1, z1);
		pos1.setPitch(pitch1);
		pos1.setYaw(yaw1);
		
		p1.setHealth(20);
		p1.teleport(pos1);
		
		World w2 = Bukkit.getWorld(data.getString("Pos2.World"));
		double x2 = data.getDouble("Pos2.X");
		double y2 = data.getDouble("Pos2.Y");
		double z2 = data.getDouble("Pos2.Z");
		float yaw2 = (float) data.getDouble("Pos2.Yaw");
		float pitch2 = (float) data.getDouble("Pos2.Pitch");
		
		Location pos2 = new Location(w2, x2, y2, z2);
		pos2.setPitch(pitch2);
		pos2.setYaw(yaw2);
		
		p2.setHealth(20);
		p2.teleport(pos2);
	}

}
