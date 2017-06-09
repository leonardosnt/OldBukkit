package com.outlook.devleeo.lsguerreiro;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Comandos implements CommandExecutor {

  Data data = Data.getInstance();
  public static ArrayList<Player> participantes = new ArrayList<>();
  static boolean acontecendo = false;
  static boolean fechado;
  static boolean pvpoff = true;
  static BukkitTask iniciando = null;
  static BukkitTask pvpativado = null;
  static BukkitTask eventofechado = null;

  public boolean onCommand(CommandSender sender, Command command, String LsCommand, String[] args) {

    if (LsCommand.equalsIgnoreCase("guerreiro")) {

      if (args.length == 0 && !sender.hasPermission("lsguerreiro.iniciar")) {
        sender.sendMessage(Mensagens.prefix + "§b Comandos:");
        sender.sendMessage("§3 /guerreiro participar §7- §bEntra no evento guerreiro.");
        sender.sendMessage("§3 /guerreiro status §7- §bMostra o status do evento guerreiro.");
        return true;
      }

      if (args.length == 0 && sender.hasPermission("lsguerreiro.iniciar")) {
        sender.sendMessage(Mensagens.prefix + "§b Comandos:");
        sender.sendMessage("§3 /guerreiro participar §7- §bEntra no evento guerreiro.");
        sender.sendMessage("§3 /guerreiro status §7- §bMostra o status do evento guerreiro.");
        sender.sendMessage("§3 /guerreiro iniciar §7- §bInicia o evento guerreiro.");
        sender.sendMessage("§3 /guerreiro forcestop §7- §bForça a finalização do guerreiro.");
        sender.sendMessage("§3 /guerreiro forcestart §7- §bForça a inicialização do guerreiro.");
        sender.sendMessage("§3 /guerreiro setentrada §7- §bSeta o local de entrada do guerreiro.");
        sender.sendMessage("§3 /guerreiro setsaida §7- §bSeta o local de saida do guerreiro.");
        return true;
      }

      if (!sender.hasPermission("lsguerreiro.iniciar") && !(args[0].equalsIgnoreCase("participar")) && !(args[0].equalsIgnoreCase("status"))) {
        sender.sendMessage(Mensagens.prefix + "§b Comandos:");
        sender.sendMessage("§3 /guerreiro participar §7- §bEntra no evento guerreiro.");
        sender.sendMessage("§3 /guerreiro status §7- §bMostra o status do evento guerreiro.");
        return true;
      }

      if (sender.hasPermission("lsguerreiro.iniciar") && !(args[0].equalsIgnoreCase("participar")) && !(args[0].equalsIgnoreCase("status"))
          && !(args[0].equalsIgnoreCase("forcestop")) && !(args[0].equalsIgnoreCase("forcestart")) && !(args[0].equalsIgnoreCase("iniciar"))
          && !(args[0].equalsIgnoreCase("setentrada")) && !(args[0].equalsIgnoreCase("setsaida"))) {
        sender.sendMessage(Mensagens.prefix + "§b Comandos:");
        sender.sendMessage("§3 /guerreiro participar §7- §bEntra no evento guerreiro.");
        sender.sendMessage("§3 /guerreiro status §7- §bMostra o status do evento guerreiro.");
        sender.sendMessage("§3 /guerreiro iniciar §7- §bInicia o evento guerreiro.");
        sender.sendMessage("§3 /guerreiro forcestop §7- §bForça a finalização do guerreiro.");
        sender.sendMessage("§3 /guerreiro forcestart §7- §bForça a inicialização do guerreiro.");
        sender.sendMessage("§3 /guerreiro setentrada §7- §bSeta o local de entrada do guerreiro.");
        sender.sendMessage("§3 /guerreiro setsaida §7- §bSeta o local de saida do guerreiro.");
        return true;
      }

      if (args[0].equalsIgnoreCase("iniciar")) {
        if (sender.hasPermission("lsguerreiro.iniciar")) {
          if (acontecendo == true) {
            sender.sendMessage(Mensagens.acontecendo);
            return true;
          }
          if (data.getData().getString("saida.world") == null || data.getData().getString("entrada.world") == null) {
            sender.sendMessage(Mensagens.prefix + "§c Você precisa setar a entrada e saida antes de iniciar o evento.");
            return true;
          }
          acontecendo = true;
          fechado = false;
          pvpoff = true;

          for (String iniciando : Main.getInstance().getConfig().getStringList("Iniciando")) {
            iniciando = iniciando.replace("&", "§").replace("{prefix}", Mensagens.prefix);
            Bukkit.broadcastMessage(iniciando);
          }

          eventofechado = new BukkitRunnable() {

            public void run() {

              int mpl = Main.getInstance().getConfig().getInt("MinimoPlayers");
              if (participantes.size() < mpl) {
                for (Player all : participantes) {
                  Utils.teleportSaida(all);
                }
                for (String mp : Main.getInstance().getConfig().getStringList("MinPlayers")) {
                  mp = mp.replace("&", "§").replace("{prefix}", Mensagens.prefix);
                  Bukkit.broadcastMessage(mp);
                }
                Utils.finalizar();
                return;
              }
              for (String fechado : Main.getInstance().getConfig().getStringList("EventoFechado")) {
                fechado = fechado.replace("&", "§").replace("{prefix}", Mensagens.prefix);
                Bukkit.broadcastMessage(fechado);
              }
              fechado = true;
            }
          }.runTaskLater(Main.getInstance(), Main.getInstance().getConfig().getInt("DelayIniciarEvento") * 20);

          pvpativado = new BukkitRunnable() {

            public void run() {
              for (String pvpativado : Main.getInstance().getConfig().getStringList("PvpAtivado")) {
                pvpativado = pvpativado.replace("&", "§").replace("{prefix}", Mensagens.prefix);
                Bukkit.broadcastMessage(pvpativado);
              }
              pvpoff = false;
              Utils.checkVivos();
            }
          }.runTaskLater(Main.getInstance(), Main.getInstance().getConfig().getInt("DelayAtivarPvp") * 20 + Main.getInstance().getConfig().getInt("DelayIniciarEvento") * 20);
          return true;
        } else {
          sender.sendMessage(Mensagens.semPerm);
        }
      }

      if (args[0].equalsIgnoreCase("forcestop")) {
        if (sender.hasPermission("lsguerreiro.forcestop")) {
          if (acontecendo == false) {
            sender.sendMessage(Mensagens.nAcontecendo);
            return true;
          }

          for (Player all : participantes) {
            Utils.teleportSaida(all);
          }
          for (String finalizadopa : Main.getInstance().getConfig().getStringList("FinalizadoPorAdm")) {
            finalizadopa = finalizadopa.replace("&", "§").replace("{prefix}", Mensagens.prefix);
            Bukkit.broadcastMessage(finalizadopa);
          }
          Utils.finalizar();
          return true;
        } else {
          sender.sendMessage(Mensagens.semPerm);
        }
      }

      if (args[0].equalsIgnoreCase("forcestart")) {
        if (sender.hasPermission("lsguerreiro.forcestart")) {
          if (acontecendo == false) {
            sender.sendMessage(Mensagens.nAcontecendo);
            return true;
          }

          if (participantes.size() < Main.getInstance().getConfig().getInt("MinimoPlayers")) {
            sender
                .sendMessage(Mensagens.prefix + " § necessário ter no m§nimo " + Main.getInstance().getConfig().getInt("MinimoPlayers") + " participantes para iniciar o evento.");
            return true;
          }

          for (String pvpativado : Main.getInstance().getConfig().getStringList("PvpAtivado")) {
            pvpativado = pvpativado.replace("&", "§").replace("{prefix}", Mensagens.prefix);
            Bukkit.broadcastMessage(pvpativado);
          }
          acontecendo = true;
          fechado = true;
          pvpoff = false;
          eventofechado.cancel();
          pvpativado.cancel();
          return true;
        } else {
          sender.sendMessage(Mensagens.semPerm);
        }
      }

      if (args[0].equalsIgnoreCase("status")) {
        if (sender.hasPermission("lsguerreiro.status")) {
          String vivos = String.valueOf(participantes.size());
          String acontecendo2 = String.valueOf(acontecendo);
          for (String status : Main.getInstance().getConfig().getStringList("GuerreiroStatus")) {
            status = status.replace("&", "§").replace("{prefix}", Mensagens.prefix).replace("{vivos}", vivos).replace("{acontecendo}", acontecendo2);
            sender.sendMessage(status);
          }
          return true;
        } else {
          sender.sendMessage(Mensagens.semPerm);
        }
      }

      if (!(sender instanceof Player)) {
        sender.sendMessage("§cEste comando nao pode ser usado pelo console.");
        return true;
      }

      final Player p = (Player) sender;
      PlayerInventory pi = p.getInventory();

      if (args[0].equalsIgnoreCase("participar")) {
        if (p.hasPermission("lsguerreiro.participar")) {

          if (participantes.contains(p)) {
            p.sendMessage(Mensagens.jaParticipando);
            return true;
          }

          if (acontecendo == false) {
            p.sendMessage(Mensagens.nAcontecendo);
            return true;
          }

          if (fechado == true) {
            p.sendMessage(Mensagens.nAberto);
            return true;
          }

          for (PotionEffect potions : p.getActivePotionEffects()) {
            p.removePotionEffect(potions.getType());
          }

          if (Main.getInstance().getConfig().getBoolean("ItemsProprios") == false) {

            if (p.getInventory().getHelmet() != null || p.getInventory().getChestplate() != null || p.getInventory().getLeggings() != null || p.getInventory().getBoots() != null) {
              p.sendMessage(Mensagens.eInv);
              return true;
            }

            if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
              p.sendMessage(Mensagens.eInv);
              return true;
            }

            for (ItemStack itens : p.getInventory().getContents()) {
              if (itens != null) {
                p.sendMessage(Mensagens.eInv);
                return true;
              }
            }

            pi.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
            pi.setItem(1, new ItemStack(Material.GOLDEN_APPLE, 10));
            pi.setItem(2, new ItemStack(Material.BOW, 1));
            pi.setItem(9, new ItemStack(Material.ARROW, 32));
            pi.setHelmet(new ItemStack(Material.IRON_HELMET));
            pi.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            pi.setBoots(new ItemStack(Material.IRON_BOOTS));
            p.setHealth(20);
            p.setFoodLevel(20);
          }

          Utils.teleportEntrada(p);
          p.sendMessage(Mensagens.entrou);
          participantes.add(p);

          if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
            Utils.ativarClanFF(p);
          }
        } else {
          p.sendMessage(Mensagens.semPerm);
        }
      }

      if (args[0].equalsIgnoreCase("setentrada")) {
        if (p.hasPermission("lsguerreiro.setentrada")) {
          if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando nao pode ser usado pelo console.");
            return true;
          }

          data.getData().set("entrada.world", p.getLocation().getWorld().getName());
          data.getData().set("entrada.x", p.getLocation().getX());
          data.getData().set("entrada.y", p.getLocation().getY());
          data.getData().set("entrada.z", p.getLocation().getZ());
          data.getData().set("entrada.yaw", p.getLocation().getYaw());
          data.getData().set("entrada.pitch", p.getLocation().getPitch());
          data.saveData();

          p.sendMessage("§3[LsGuerreiro] §bEntrada setada.");

        } else {
          p.sendMessage(Mensagens.semPerm);
        }
      }

      if (args[0].equalsIgnoreCase("setsaida")) {
        if (p.hasPermission("lsguerreiro.setsaida")) {

          data.getData().set("saida.world", p.getLocation().getWorld().getName());
          data.getData().set("saida.x", p.getLocation().getX());
          data.getData().set("saida.y", p.getLocation().getY());
          data.getData().set("saida.z", p.getLocation().getZ());
          data.getData().set("saida.yaw", p.getLocation().getYaw());
          data.getData().set("saida.pitch", p.getLocation().getPitch());
          data.saveData();

          p.sendMessage("§3[LsGuerreiro] §bSaida setada.");
        } else {
          p.sendMessage(Mensagens.semPerm);
        }
      }
    }

    return false;

  }

}
