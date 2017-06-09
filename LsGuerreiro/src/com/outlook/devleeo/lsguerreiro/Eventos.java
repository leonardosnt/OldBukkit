package com.outlook.devleeo.lsguerreiro;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Eventos implements Listener {

  Data data = Data.getInstance();

  @EventHandler
  void onDeath(final PlayerDeathEvent e) {
    if (Comandos.participantes.contains(e.getEntity())) {
      Utils.checkVivos();
      if (Main.getInstance().getConfig().getBoolean("MensagemMorte.Ativar") == true) {
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("MensagemMorte.Mensagem").replace("{prefix}", Mensagens.prefix).replace("&", "§")
            .replace("{morto}", e.getEntity().getName()));
      }
      Comandos.participantes.remove(e.getEntity());
      if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
        Utils.desativarClanFF(e.getEntity());
      }
    }
  }

  @EventHandler
  void onQuit(PlayerQuitEvent e) {
    if (Comandos.participantes.contains(e.getPlayer())) {
      Utils.checkVivos();
      Comandos.participantes.remove(e.getPlayer());
      if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
        Utils.desativarClanFF(e.getPlayer());
      }
    }
  }

  @EventHandler
  void onDamage(EntityDamageEvent e) {
    if (Comandos.pvpoff == true && Comandos.participantes.contains(e.getEntity())) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  void onPvPOFF(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof Player) {
      if (Comandos.pvpoff == true && Comandos.participantes.contains(e.getEntity()) && Comandos.participantes.contains(e.getDamager())) {
        Player p = (Player) e.getDamager();
        p.sendMessage(Main.getInstance().getConfig().getString("PvpDesativado").replace("&", "§").replace("{prefix}", Mensagens.prefix));
      }
    }
  }

  @EventHandler
  void onTeleport(PlayerTeleportEvent e) {
    if (Comandos.acontecendo == true && Comandos.participantes.contains(e.getPlayer())) {
      if (Main.getInstance().getConfig().getBoolean("BloquearTeleporte.Ativar") == true) {
        e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("BloquearTeleporte.Mensagem").replace("{prefix}", Mensagens.prefix).replace("&", "§"));
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  void onChat(ChatMessageEvent e) {
    if (e.getTags().contains("lsguerreiro") && e.getSender().getName().equalsIgnoreCase(data.getData().getString("GuerreiroAtual"))) {
      e.setTagValue("lsguerreiro", Main.getInstance().getConfig().getString("Tag"));
    }
  }

  @EventHandler
  void onCommandProcess(PlayerCommandPreprocessEvent e) {
    if (Comandos.participantes.contains(e.getPlayer())) {
      List<String> cmds = Main.getInstance().getConfig().getStringList("ComandosBloqueados");
      for (String cmd : cmds) {
        if (e.getMessage().equalsIgnoreCase("/" + cmd)) {
          e.setCancelled(true);
          e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("ComandoBloqueado").replace("{prefix}", Mensagens.prefix).replace("&", "§"));
        }
      }
    }

  }
}
