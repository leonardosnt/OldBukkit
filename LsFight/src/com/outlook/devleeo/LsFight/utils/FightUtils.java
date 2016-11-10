package com.outlook.devleeo.LsFight.utils;

import static com.outlook.devleeo.LsFight.utils.MensagensUtils.*;
import static com.outlook.devleeo.LsFight.utils.TeleportUtils.*;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsFight.LsFight;
import com.outlook.devleeo.LsFight.outros.Files;
import com.outlook.devleeo.LsFight.outros.Hooks;

public class FightUtils {
	
	private static boolean acontecendo = false;
	private static boolean aberto = false;
	public static ArrayList<Player> participantes = new ArrayList<>();
	public static ArrayList<Player> lutadores = new ArrayList<>();
	
	static Files files = Files.getInstance();
	
	public static boolean isAcontecendo() {
		return acontecendo;
	}
	
	public static boolean isAberto() {
		return aberto;
	}
	
	public static void setAcontecendo(boolean b) {
		acontecendo = b;
	}
	
	public static void setAberto(boolean b) {
		aberto = b;
	}

	public static void finalizar() {
		setAberto(false);
		setAcontecendo(false);
		participantes.clear();
		lutadores.clear();
		Bukkit.getScheduler().cancelTasks(LsFight.getInstance());
	}
	
	public static void iniciando(final int numeroAvisos) {
		if (numeroAvisos != 0) {
			setAcontecendo(true);
			setAberto(true);
			for(String msg : getIniciandoMensagens()) {
				Bukkit.broadcastMessage(formatMessage(msg).replace("<restante>", Integer.toString(LsFight.getInstance().getConfig().getInt("Iniciando.TempoEntreAvisos") * numeroAvisos)));
			}
			Bukkit.getServer().getScheduler().runTaskLater(LsFight.getInstance(), new Runnable() {
				
				public void run() {
					iniciando(numeroAvisos - 1);
				}
				
			}, LsFight.getInstance().getConfig().getInt("Iniciando.TempoEntreAvisos") * 20);
		} else {
			iniciar();
		}
	}
	
	public static void iniciar() {
		if (participantes.size() >= LsFight.getInstance().getConfig().getInt("MinimoParticipantes")) {
			for (String msg : getIniciadoMensagens()) {
				Bukkit.broadcastMessage(formatMessage(msg));
			}
			iniciarLuta();
			setAberto(false);
			
		} else {
			for (String msg : getMinPlayerMensagens()) {
				Bukkit.broadcastMessage(formatMessage(msg.replace("<min>", Integer.toString(LsFight.getInstance().getConfig().getInt("MinimoParticipantes")))));
			}
			for (Player p : participantes) {
				teleportSaida(p);
			}
			finalizar();
		}
	}
	
	public static void iniciarLuta() {
		for (String msg : getProximaLutaMensagens()) {
			Bukkit.broadcastMessage(formatMessage(msg.replace("<tempo>", Integer.toString(LsFight.getInstance().getConfig().getInt("TempoEntreLutas")))));
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Random r = new Random();
				Player lutador1 = participantes.get(r.nextInt(participantes.size()));
				Player lutador2 = participantes.get(r.nextInt(participantes.size()));
				
				while (lutador1 == lutador2) {
					lutador1 = participantes.get(r.nextInt(participantes.size()));
				}
				
				lutadores.add(lutador1);
				lutadores.add(lutador2);
				
				for (String msg : getLutaMensagens()) {
					Bukkit.broadcastMessage(formatMessage(msg.replace("<lutador1>", lutador1.getName()).replace("<lutador2>", lutador2.getName())));
				}
			
				teleportLutadores(lutador2, lutador1);
				giveItem(lutador1);
				giveItem(lutador2);
			}
		}.runTaskLater(LsFight.getInstance(), LsFight.getInstance().getConfig().getInt("TempoEntreLutas") * 20);
		
	}
	
	public static void venceuDuelo(Player vencedor) {
		if (lutadores.get(1) == vencedor) {
			for (String msg  : getMorreuMensagens()) {
				Bukkit.broadcastMessage(formatMessage(msg).replace("<vencedor>", vencedor.getName()).replace("<perdedor>", lutadores.get(0).getName()));
			}
		} else {
			for (String msg  : getMorreuMensagens()) {
				Bukkit.broadcastMessage(formatMessage(msg).replace("<vencedor>", vencedor.getName()).replace("<perdedor>", lutadores.get(1).getName()));
			}
		}
		
		teleportEntrada(vencedor);
		vencedor.getInventory().clear();
		lutadores.clear();
		
		if (participantes.size() == 1) {
			setVencedor(participantes.get(0));
			finalizar();
			return;
		}
		
		iniciarLuta();
	}
	
	
	public static void setVencedor(Player p) {
		for (String msg : getVenceuMensagens()) {
			Bukkit.broadcastMessage(formatMessage(msg).replace("<vencedor>", p.getName()));
		}
		
		if (LsFight.getInstance().getConfig().getBoolean("Premios.Dinheiro.Ativar")) {
			Hooks.eco.depositPlayer(participantes.get(0).getName(), LsFight.getInstance().getConfig().getInt("Premios.Dinheiro.Quantidade"));
		}
		
		files.getDataFile().set("Vencedor", p.getName());
		files.saveDataFile();
		
		PlayerUtils.desativarClanFF(p);
		
	}
	
	public static void giveItem(Player p) {
		if (LsFight.getInstance().getConfig().getStringList("Items") == null)return;
		
		for(String lista : LsFight.getInstance().getConfig().getStringList("Items")) {//inicio items
			
			String[] item = lista.split(" ");
			ItemStack i;

			try {
				if (item.length == 2) {
					i = new ItemStack(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
					p.getInventory().addItem(i);
				}
			} catch (Exception e) {
				Bukkit.broadcastMessage("§5[LsFight] §cErro na configuracao dos items.");
			}
		}
	}
}
