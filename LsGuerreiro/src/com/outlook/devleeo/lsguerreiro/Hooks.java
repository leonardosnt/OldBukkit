package com.outlook.devleeo.lsguerreiro;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class Hooks {

  public static SimpleClans sc;
  public static Economy eco;
  public static Chat chat;

  public static void hookSimpleClans() {
    if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
      Bukkit.getServer().getConsoleSender().sendMessage("§3[LsGuerreiro] §bSimpleClans encontrado, Hooked.");
      sc = ((SimpleClans) Bukkit.getServer().getPluginManager().getPlugin("SimpleClans"));
    }
  }

  public static void hookEconomy() {
    RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      Bukkit.getConsoleSender().sendMessage("§3[LsGuerreiro] §bVault encontrado, Hooked Economy.");
      eco = (Economy) economyProvider.getProvider();
    }
  }

  public static boolean hookChat() {
    RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
    if (chatProvider != null) {
      Bukkit.getConsoleSender().sendMessage("§3[LsGuerreiro] §bVault encontrado, Hooked Chat.");
      chat = chatProvider.getProvider();
    }
    return (chat != null);
  }
}
