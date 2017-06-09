package com.outlook.devleeo.lsguerreiro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin {

  private static Main instance;
  Data data = Data.getInstance();
  public static Scoreboard board;
  public static Team gue;

  public void onEnable() {

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    board = manager.getNewScoreboard();

    final Objective objective = board.registerNewObjective("test1", "teste2");
    objective.setDisplayName("");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    gue = board.registerNewTeam("gue");
    gue.setPrefix(ChatColor.DARK_AQUA + "[Guerreiro] " + ChatColor.RESET);

    Main.instance = this;
    data.setup(this);
    saveDefaultConfig();
    Hooks.hookEconomy();
    Hooks.hookChat();
    Hooks.hookSimpleClans();
    Utils.scoreBoard();
    Utils.atualizarTag();

    if (Bukkit.getPluginManager().getPlugin("Legendchat") == null) {
      getServer().getConsoleSender().sendMessage("§4[LsGuerreiro] §cLegendChat nao encontrado, tag desativada.");
    }

    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Plugin habilitado com sucesso.");
    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Plugin criado por DevLeeo!");
    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Versao: " + getDescription().getVersion());

    getCommand("guerreiro").setExecutor(new Comandos());
    getServer().getPluginManager().registerEvents(new Eventos(), this);
  }

  public void onDisable() {
    getServer().getConsoleSender().sendMessage("");
    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Plugin desabilitado com sucesso.");
    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Plugin criado por DevLeeo!");
    getServer().getConsoleSender().sendMessage("§3[LsGuerreiro]§b Versao: " + getDescription().getVersion());
    getServer().getConsoleSender().sendMessage("");

  }

  public static Main getInstance() {
    return Main.instance;
  }
}
