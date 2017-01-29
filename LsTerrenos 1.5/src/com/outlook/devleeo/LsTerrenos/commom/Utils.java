package com.outlook.devleeo.LsTerrenos.commom;

import static com.outlook.devleeo.LsTerrenos.managers.TerrenosManager.isInProtectedRegion;
import static com.outlook.devleeo.LsTerrenos.managers.TerrenosManager.isInYourRegion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsTerrenos.Main;
import com.outlook.devleeo.LsTerrenos.managers.FilesManager;
import com.sk89q.worldedit.BlockVector;

public class Utils {

	public static int getQuantidadeTerrenos(Player p) {
		return Main.getPlugin().getConfig()
				.getInt("Grupos." + Hooks.getPermissions().getPrimaryGroup(p));
	}

	public static boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	public static void gerarTerreno(Location loc, int tamanho) {
		int a = tamanho / 2;
		for (int x = -a; x <= a; x++) {
			for (int z = -a; z <= a; z++) {
				if (!Main.getPlugin().getConfig()
						.getBoolean("Gerar_Apenas_Cercas")) {
					for (int y = 0; y <= 20; y++) {
						loc.clone().add(x, -1, z).getBlock()
								.setType(Material.GRASS);
						loc.clone().add(x, y, z).getBlock()
								.setType(Material.AIR);
					}
				}
				loc.clone().add(x, 0, a).getBlock().setType(Material.FENCE);
				loc.clone().add(x, 0, -a).getBlock().setType(Material.FENCE);
				loc.clone().add(a, 0, z).getBlock().setType(Material.FENCE);
				loc.clone().add(-a, 0, z).getBlock().setType(Material.FENCE);

			}
		}
	}

	public static boolean isaVenda(World w, BlockVector bvmin, BlockVector bvmax) {
		Location min = new Location(w, bvmin.getX(), bvmin.getY(), bvmin.getZ());
		Location max = new Location(w, bvmax.getX(), bvmax.getY(), bvmax.getZ());

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
				for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
					Block b = w.getBlockAt(x, y, z);
					if (b.getType() == Material.SIGN_POST && ((Sign) b.getState()).getLine(0).equals(getMensagem("placa_venda.linha1"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void delTerreno(World w, BlockVector bvmin, BlockVector bvmax) {
		Location min = new Location(w, bvmin.getX(), bvmin.getY(), bvmin.getZ());
		Location max = new Location(w, bvmax.getX(), bvmax.getY(), bvmax.getZ());

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
				for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
					Block b = w.getBlockAt(x, y, z);
					List<String> s = Main.getPlugin().getConfig().getStringList("SafeBlocks");
					if (!s.contains(Integer.toString(b.getTypeId()))) {
						b.setType(Material.AIR);
					}
				}
			}
		}
	}

	public static List<String> getComandosBloqueados(String player,
			String terreno) {
		if (FilesManager.getDataFile().getStringList(
				player.toLowerCase() + "." + terreno.toLowerCase()) == null) {
			return null;
		}
		return FilesManager.getDataFile().getStringList(
				player.toLowerCase() + "." + terreno.toLowerCase());
	}

	public static void addComandoBloqueado(String player, String terreno,
			String comando) {
		List<String> comandos = new ArrayList<>();

		if (getComandosBloqueados(player, terreno) != null) {
			comandos = getComandosBloqueados(player, terreno);
		}

		comandos.add(comando.startsWith("/") ? comando : "/" + comando);
		FilesManager.getDataFile().set(
				player.toLowerCase() + "." + terreno.toLowerCase(), comandos);
		FilesManager.saveDataFile();
	}

	public static void removeComandoBloqueado(String player, String terreno,
			String comando) {
		List<String> comandos = new ArrayList<>();

		if (getComandosBloqueados(player, terreno) != null) {
			comandos = getComandosBloqueados(player, terreno);
		}

		if (comandos
				.contains(comando.startsWith("/") ? comando : "/" + comando)) {
			comandos.remove(comando.startsWith("/") ? comando : "/" + comando);
			FilesManager.getDataFile().set(
					player.toLowerCase() + "." + terreno.toLowerCase(),
					comandos);
			FilesManager.saveDataFile();
		}

	}

	public static String format(String s) {
		return s.replace("&", "§").replace("\\n", "\n").replace("$>>", "»");
	}

	public static boolean isWorldBlocked(World w) {
		return Main.getPlugin().getConfig().getStringList("MundosBloqueados")
				.contains(w.getName());
	}

	public static boolean isAuthorized(CommandSender sender, String cmd) {
		return sender.hasPermission("lsterrenos." + cmd);
	}

	public static boolean isAuthorized(Player p, String cmd) {
		return (isAdmin(p) || p.hasPermission("lsterrenos." + cmd));
	}

	public static boolean isAdmin(CommandSender sender) {
		return (sender.isOp() || sender.hasPermission("lsterrenos.admin"));
	}

	public static boolean isAdmin(Player p) {
		return (p.isOp() || p.hasPermission("lsterrenos.admin"));
	}

	public static void logError(String error) {
		Bukkit.getLogger().severe("[LsTerrenos] " + error);
	}

	public static void log(String msg) {
		Bukkit.getLogger().info("[LsTerrenos] " + msg);
	}

	public static String getMensagem(String path) {
		return format(FilesManager.getMensagensFile().getString(
				path.toLowerCase()));
	}

	public static int getPreco(int tamanho) {
		return tamanho * Main.getPlugin().getConfig().getInt("Preco_Por_Bloco");
	}

	public static void sendHelp(CommandSender sender) {
		for (String s : FilesManager.getMensagensFile().getStringList("usage")) {
			if (s.contains("admin") && !isAdmin(sender)) {
				continue;
			}
			if (s.contains("comprar") && !isAuthorized(sender, "comprar")) {
				continue;
			}
			if (s.contains("vender") && !isAuthorized(sender, "vender")) {
				continue;
			}
			if (s.contains("deletar") && !isAuthorized(sender, "deletar")) {
				continue;
			}
			if (s.contains("addamigo") && !isAuthorized(sender, "addamigo")) {
				continue;
			}
			if (s.contains("delamigo") && !isAuthorized(sender, "delamigo")) {
				continue;
			}
			if (s.contains("info") && !isAuthorized(sender, "info")) {
				continue;
			}
			if (s.contains("lista") && !isAuthorized(sender, "lista")) {
				continue;
			}
			if (s.contains("tp") && !isAuthorized(sender, "tp")) {
				continue;
			}
			if (s.contains("pvp") && !isAuthorized(sender, "pvp")) {
				continue;
			}
			if (s.contains("blockcmds") && !isAuthorized(sender, "blockcmds")) {
				continue;
			}
			if (!isAdmin(sender) && isPlayer(sender)) {
				Player p = (Player) sender;
				if (!isInProtectedRegion(p)
						&& (s.contains("addamigo") || s.contains("delamigo")
								|| s.contains("pvp") || s.contains("blockcmds")
								|| s.contains("vender") || s
									.contains("deletar"))) {
					continue;
				} else if (isInProtectedRegion(p)
						&& !isInYourRegion(p)
						&& (s.contains("deletar") || s.contains("comprar")
								|| s.contains("addamigo")
								|| s.contains("delamigo") || s.contains("pvp")
								|| s.contains("blockcmds") || s
									.contains("vender"))) {
					continue;
				} else if (isInProtectedRegion(p) && isInYourRegion(p)
						&& (s.contains("comprar"))) {
					continue;
				}
			}
			sender.sendMessage(format(s));
		}
	}
}