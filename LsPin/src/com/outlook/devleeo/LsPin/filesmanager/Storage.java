package com.outlook.devleeo.LsPin.filesmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.bukkit.Bukkit;

import com.outlook.devleeo.LsPin.LsPin;
import com.outlook.devleeo.LsPin.pinManager.Pin;

public class Storage {
	
	private static final File PASTA = LsPin.getInstance().getDataFolder();
	public static final Path PINS_FILE = Paths.get(PASTA + File.separator + "Pins.ls");
	public static void setupFiles() {
		if (!PASTA.exists()) {
			PASTA.mkdir();
		}
	}
	
	public static void armazenarPins(Map<String, String> pins) throws Exception{
		try(FileOutputStream fos = new FileOutputStream(PASTA + File.separator + "Pins.ls")) {
			try(ObjectOutputStream oos = new ObjectOutputStream(fos)){
				oos.writeObject(pins);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> recuperarPins() throws Exception {
		try(FileInputStream fis = new FileInputStream(PASTA + File.separator + "Pins.ls")) {
			try(ObjectInputStream ois = new ObjectInputStream(fis)) {
				return (Map<String, String>) ois.readObject();
			}
		}
	}
	
	public static void iniciarTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(LsPin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.getConsoleSender().sendMessage(Config.getPrefix() + " §bPins salvos com sucesso.");
				try {
					armazenarPins(Pin.getPins());
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage("§cOcorreu um erro ao armazenar os Pins, Erro:");
					e.printStackTrace();
				}
			}
		}, 200, Config.TEMPO_SALVAR_PINS * 60 * 20);
	}
}
