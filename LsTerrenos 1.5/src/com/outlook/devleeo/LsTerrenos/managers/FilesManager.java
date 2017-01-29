package com.outlook.devleeo.LsTerrenos.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsTerrenos.commom.Utils;
import com.outlook.devleeo.LsTerrenos.commom.crypt.Base64Coder;
import com.outlook.devleeo.LsTerrenos.commom.http.HTTPPost;

public class FilesManager {
	
	private static File mfile;
	private static File dfile;
	private static FileConfiguration mensagens;
	private static FileConfiguration data;
	
	public static void setup(final Plugin p)  {
		File pluginFolder = p.getDataFolder();
		File casasFolder = new File(p.getDataFolder() + File.separator + "casas");
		File read = new File(casasFolder.getAbsolutePath(), "_readMe.txt");
		
		if (!pluginFolder.exists()) {
			pluginFolder.mkdir();
		}
		
		if (!casasFolder.exists()) {
			casasFolder.mkdir();
			p.saveResource("casas\\casa1_10-14.schematic", true);
			p.saveResource("casas\\casa1_15-19.schematic", true);
			p.saveResource("casas\\casa1_20-24.schematic", true);
		}
		
		if (!read.exists()) {
			try {
				read.createNewFile();	
			} catch (IOException e) {
				e.printStackTrace();
			}
			try(PrintWriter fw = new PrintWriter(new FileWriter(read))) {
				fw.println("Os nomes dos schematics devem ser neste padrao.");
				fw.println("(qualquer nome)_(tamanho minino que a casa cabe)-(tamanho maximo que a casa cabe).schematic");
				fw.println("Por exemplo casa1_10-15.schematic essa casa poderia vir em terrenos de 10 a 15...");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		mfile = new File(pluginFolder, "mensagens.yml");
		dfile = new File(pluginFolder, "data.yml");
		
     /*  
      Sistema anti-pirataria zuado, mas que ninguem burlou e.e
      
     new Thread() {
            @Override
            public void run() {
                try {
                    final HTTPPost pm = new HTTPPost(new String(Base64Coder.decodeString("aHR0cDovL3d3dy5kZXZsZWVvLmdhL3V0aWxzL2xpY2VuY2UucGhw")));
                    pm.addParameter("data", "newvs;" + LsTerrenos.getLicence());
                    pm.addParameter("plugin", "lsterrenos");
                    final URLConnection response = pm.post();
                    if (new BufferedReader(new InputStreamReader(response.getInputStream())).readLine().equals("false")) {
                        System.err.println(String.valueOf(Base64Coder.decodeString("W0xzVGVycmVub3Nd")) + " " + Base64Coder.decodeString("TGljZW5jYSBpbnZhbGlkYSEhIQ=="));
                        System.err.println(String.valueOf(Base64Coder.decodeString("W0xzVGVycmVub3Nd")) + " " + Base64Coder.decodeString("TGljZW5jYSBpbnZhbGlkYSEhIQ=="));
                        System.err.println(String.valueOf(Base64Coder.decodeString("W0xzVGVycmVub3Nd")) + " " + Base64Coder.decodeString("TGljZW5jYSBpbnZhbGlkYSEhIQ=="));
                        System.err.println(String.valueOf(Base64Coder.decodeString("W0xzVGVycmVub3Nd")) + " " + Base64Coder.decodeString("TGljZW5jYSBpbnZhbGlkYSEhIQ=="));
                        Bukkit.getPluginManager().disablePlugin(p);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
      */
		
		if (!mfile.exists()) {
			p.saveResource("mensagens.yml", true);
		}
		
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Utils.logError("Ocorreu um erro ao criar o arquivo data.yml");
				e.printStackTrace();
			}
		}
		
		data = YamlConfiguration.loadConfiguration(dfile);
		mensagens = YamlConfiguration.loadConfiguration(mfile);
	}
	
	public static FileConfiguration getMensagensFile() {
		return mensagens;
	}
	
	public static FileConfiguration getDataFile() {
		return data;
	}
	
	public static void saveDataFile() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			Utils.logError("Ocorreu um erro ao salvar o arquivo data.yml");
			e.printStackTrace();
		}
	}
	
	public static void saveMensagensFile() {
		try {
			mensagens.save(mfile);
		} catch (IOException e) {
			Utils.logError("Nao foi possivel salvar o arquivo mensagens.yml");
			e.printStackTrace();
		}
	}
}
