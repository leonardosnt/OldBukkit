package com.outlook.devleeo.lsguerreiro;

public class Mensagens {

  public static String prefix = Main.getInstance().getConfig().getString("Prefix").replace("&", "§");
  public static String acontecendo = Main.getInstance().getConfig().getString("Acontecendo").replace("&", "§").replace("{prefix}", prefix);
  public static String nAcontecendo = Main.getInstance().getConfig().getString("nAcontecendo").replace("&", "§").replace("{prefix}", prefix);
  public static String jaParticipando = Main.getInstance().getConfig().getString("JaParticipando").replace("&", "§").replace("{prefix}", prefix);
  public static String nAberto = Main.getInstance().getConfig().getString("NaoAberto").replace("&", "§").replace("{prefix}", prefix);
  public static String eInv = Main.getInstance().getConfig().getString("EsvazieInv").replace("&", "§").replace("{prefix}", prefix);
  public static String entrou = Main.getInstance().getConfig().getString("EntrouNoGuerreiro").replace("&", "§").replace("{prefix}", prefix);
  public static String semPerm = Main.getInstance().getConfig().getString("SemPerm").replace("&", "§").replace("{prefix}", prefix);
}
