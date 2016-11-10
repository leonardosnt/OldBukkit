package com.outlook.devleeo.LsMvP.outros;

import static com.outlook.devleeo.LsMvP.metodos.Metodos.getEntrada;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.getSaida;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.getSpawnMob;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.setAcontecendo;
import static com.outlook.devleeo.LsMvP.metodos.Metodos.setIniciado;

import java.util.Calendar;

import org.bukkit.Bukkit;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.metodos.Metodos;

public class AutoStart {

	private static int toInt(String s) {
		return Integer.parseInt(s) < 10 ? Integer.parseInt(s.substring(1)) : Integer.parseInt(s);
	}
	
	private static int getDia(String dia) {
		switch (dia.toLowerCase()) {
			case "segunda":
				return 2;
			case "terca":
				return 3;
			case "quarta":
				return 4;				
			case "quinta":
				return 5;				
			case "sexta":
				return 6;				
			case "sabado":
				return 7;				
			case "domingo":
				return 1;				
			default:
				return 0;
		}
	}
	
	public static void checkTempo() {
		
		try {
			for (String s : Main.getInstance().getConfig().getStringList("AutoStart.Horarios")) {
				
				String[] a = s.split("-");
				String dia1;
				String[] horario1 = null;
				
				if (a.length == 2) {
					dia1 = s.split("-")[0];	
					horario1 = s.split("-")[1].split(":");
				} else {
					dia1 = "nl";
					horario1 = s.split("-")[0].split(":");
				}
				
				final String dia = dia1;
				final String[] horario = horario1;
				
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						if (dia.equals("nl")) {
							if (Calendar.getInstance().get(11) == toInt(horario[0]) && Calendar.getInstance().get(12) == toInt(horario[1])) {
								if (getEntrada() == null || getSaida() == null || getSpawnMob() == null) {
									Bukkit.getConsoleSender().sendMessage("");
									Bukkit.getConsoleSender().sendMessage("§4[MVP] §cEntrada, saida ou spawn do boss nao foi definido.");
									Bukkit.getConsoleSender().sendMessage("§4[MVP] §cO Evento so pode iniciar com todos os locais definidos.");	
									Bukkit.getConsoleSender().sendMessage("");
									return;
								}
								if (!Metodos.isIniciado()) {
									Metodos.preparar(Main.getInstance().getConfig().getInt("Iniciando.NumeroDeAvisos"));
									setIniciado(true);
									setAcontecendo(true);
								}
							}
						} else {
							if (Calendar.getInstance().get(7) == getDia(dia) && Calendar.getInstance().get(11) == toInt(horario[0]) && Calendar.getInstance().get(12) == toInt(horario[1])) {
								if (getEntrada() == null || getSaida() == null || getSpawnMob() == null) {
									Bukkit.getConsoleSender().sendMessage("");
									Bukkit.getConsoleSender().sendMessage("§4[MVP] §cEntrada, saida ou spawn do boss nao foi definido.");
									Bukkit.getConsoleSender().sendMessage("§4[MVP] §cO Evento so pode iniciar com todos os locais definidos.");
									Bukkit.getConsoleSender().sendMessage("");
									return;
								}
								if (!Metodos.isIniciado()) {
									Metodos.preparar(Main.getInstance().getConfig().getInt("Iniciando.NumeroDeAvisos"));
									setIniciado(true);
									setAcontecendo(true);
								}
							} 
						}
						
					}
				}, 0, 200);
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§4[MVP] §cErro na configuracao dos horarios.");	
		}
	}
}
