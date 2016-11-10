package com.outlook.devleeo.LsFight.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsFight.outros.Files;

public class LocationUtils {
	
	private static Files files = Files.getInstance();
	private static FileConfiguration data = files.getDataFile();
	
	public static void setEntrada(Player p) {
		data.set("Entrada.World", p.getWorld().getName());
		data.set("Entrada.X", p.getLocation().getX());
		data.set("Entrada.Y", p.getLocation().getY());
		data.set("Entrada.Z", p.getLocation().getZ());
		data.set("Entrada.Yaw", p.getLocation().getYaw());
		data.set("Entrada.Pitch", p.getLocation().getPitch());
		files.saveDataFile();
	}
	
	public static void setSaida(Player p) {
		data.set("Saida.World", p.getWorld().getName());
		data.set("Saida.X", p.getLocation().getX());
		data.set("Saida.Y", p.getLocation().getY());
		data.set("Saida.Z", p.getLocation().getZ());
		data.set("Saida.Yaw", p.getLocation().getYaw());
		data.set("Saida.Pitch", p.getLocation().getPitch());
		files.saveDataFile();
	}
	
	public static  void setPos1(Player p) {
		data.set("Pos1.World", p.getWorld().getName());
		data.set("Pos1.X", p.getLocation().getX());
		data.set("Pos1.Y", p.getLocation().getY());
		data.set("Pos1.Z", p.getLocation().getZ());
		data.set("Pos1.Yaw", p.getLocation().getYaw());
		data.set("Pos1.Pitch", p.getLocation().getPitch());
		files.saveDataFile();
	}
	
	public static void setPos2(Player p) {
		data.set("Pos2.World", p.getWorld().getName());
		data.set("Pos2.X", p.getLocation().getX());
		data.set("Pos2.Y", p.getLocation().getY());
		data.set("Pos2.Z", p.getLocation().getZ());
		data.set("Pos2.Yaw", p.getLocation().getYaw());
		data.set("Pos2.Pitch", p.getLocation().getPitch());
		files.saveDataFile();
	}
	
}
