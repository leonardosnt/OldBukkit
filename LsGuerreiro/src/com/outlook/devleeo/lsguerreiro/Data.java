package com.outlook.devleeo.lsguerreiro;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Data {

  private Data() {
  }

  static Data instance = new Data();

  public static Data getInstance() {
    return instance;
  }

  Plugin p;

  FileConfiguration data;
  File dfile;

  public void setup(Plugin p) {

    if (!p.getDataFolder().exists()) {
      p.getDataFolder().mkdir();
    }

    dfile = new File(p.getDataFolder(), "data.yml");

    if (!dfile.exists()) {
      try {
        dfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe("§cErro ao criar o arquivo data.yml");
      }
    }

    data = YamlConfiguration.loadConfiguration(dfile);
  }

  public FileConfiguration getData() {
    return data;
  }

  public void saveData() {
    try {
      data.save(dfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe("§cErro ao salvar o arquivo data.yml");
    }
  }

  public void reloadData() {
    data = YamlConfiguration.loadConfiguration(dfile);
  }
}