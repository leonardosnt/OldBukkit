package com.outlook.devleeo.lsguerreiro;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

public class Utils {

  public static Data data = Data.getInstance();
  static int premio = Main.getInstance().getConfig().getInt("Premio");

  public static ScoreboardManager manager = Bukkit.getScoreboardManager();

  public static void setWinner(final Player newguerreiro) {

    for (String msg : Main.getInstance().getConfig().getStringList("GuerreiroFinalizado")) {
      Bukkit.broadcastMessage(msg.replace("&", "§").replace("{prefix}", Mensagens.prefix).replace("{player}", newguerreiro.getName()));
    }

    Hooks.eco.depositPlayer(newguerreiro.getName(), premio);

    data.getData().set("GuerreiroAtual", newguerreiro.getName());
    data.saveData();

    newguerreiro.getWorld().createExplosion(newguerreiro.getLocation(), -1);
    newguerreiro.sendMessage(Main.getInstance().getConfig().getString("TeleportadoSaida").replace("&", "§").replace("{prefix}", Mensagens.prefix));

    new BukkitRunnable() {

      @Override
      public void run() {
        teleportSaida(newguerreiro);
      }
    }.runTaskLater(Main.getInstance(), 5 * 20);
  }

  public static void finalizar() {
    Comandos.acontecendo = false;
    Comandos.fechado = false;
    Comandos.participantes.clear();
    Comandos.eventofechado.cancel();
    Comandos.pvpativado.cancel();
  }

  public static void teleportSaida(final Player p) {
    new BukkitRunnable() {

      @Override
      public void run() {
        World w = Bukkit.getWorld(data.getData().getString("saida.world"));
        double x = data.getData().getDouble("saida.x");
        double y = data.getData().getDouble("saida.y");
        double z = data.getData().getDouble("saida.z");
        float yaw = (float) data.getData().getDouble("saida.yaw");
        float pitch = (float) data.getData().getDouble("saida.pitch");

        Location saida = new Location(w, x, y, z);
        saida.setYaw(yaw);
        saida.setPitch(pitch);

        p.teleport(saida);

        if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
          desativarClanFF(p);
        }
      }
    }.runTaskLater(Main.getInstance(), 1);
  }

  public static void teleportEntrada(Player p) {
    World w = Bukkit.getServer().getWorld(data.getData().getString("entrada.world"));
    double x = data.getData().getDouble("entrada.x");
    double y = data.getData().getDouble("entrada.y");
    double z = data.getData().getDouble("entrada.z");
    float yaw = (float) data.getData().getDouble("entrada.yaw");
    float pitch = (float) data.getData().getDouble("entrada.pitch");

    Location entrada = new Location(w, x, y, z);

    entrada.setYaw(yaw);
    entrada.setPitch(pitch);

    p.teleport(entrada);
  }

  public static void desativarClanFF(Player p) {
    if (Hooks.sc.getClanManager().getClanPlayer(p) == null) {
      return;
    }
    Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(false);
  }

  public static void ativarClanFF(Player p) {
    if (Hooks.sc.getClanManager().getClanPlayer(p) == null) {
      return;
    }
    Hooks.sc.getClanManager().getClanPlayer(p).setFriendlyFire(true);
  }

  public static void checkVivos() {
    new BukkitRunnable() {

      public void run() {
        if (Comandos.acontecendo == true && Comandos.participantes.size() < 2) {
          Player winer = Comandos.participantes.get(0);

          Utils.setWinner(winer);
          Utils.finalizar();
          if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
            Utils.desativarClanFF(winer);
          }
        }
      }
    }.runTaskLater(Main.getInstance(), 1);
  }

  public static void atualizarTag() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

      @Override
      public void run() {
        for (Player online : Bukkit.getOnlinePlayers()) {

          if (online.getName().equalsIgnoreCase(data.getData().getString("GuerreiroAtual"))) {
            Main.gue.addPlayer(online);
          }

          if (!online.getName().equalsIgnoreCase(data.getData().getString("GuerreiroAtual"))) {
            if (Main.gue.hasPlayer(online)) {
              Main.gue.removePlayer(online);
            }
          }
        }

      }
    }, 0, 1 * 20);
  }

  public static void scoreBoard() {
  }
}
