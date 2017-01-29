package com.outlook.devleeo.LsTerrenos.managers;

import static com.outlook.devleeo.LsTerrenos.commom.Utils.delTerreno;
import static com.outlook.devleeo.LsTerrenos.commom.Utils.getMensagem;
import static com.outlook.devleeo.LsTerrenos.commom.Utils.getQuantidadeTerrenos;
import static com.outlook.devleeo.LsTerrenos.commom.Utils.isAdmin;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsTerrenos.Main;
import com.outlook.devleeo.LsTerrenos.commom.Hooks;
import com.outlook.devleeo.LsTerrenos.commom.Utils;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class TerrenosManager {
	
	private static WorldGuardPlugin wg = Hooks.getWorldguard();
	
	public static void comprarTerreno(Player p, int tam, String nome) {
		Location loc = p.getLocation();
	    
		int preco = Utils.getPreco(tam);
		int xma = loc.getBlockX() - tam/2;
		int xme = loc.getBlockX() + tam/2;
		int zma = loc.getBlockZ() - tam/2;
		int zme = loc.getBlockZ() + tam/2;
	    int yme = Main.getPlugin().getConfig().getInt("Altura.Min");
	    int yma = Main.getPlugin().getConfig().getInt("Altura.Max");
	    
	    RegionManager rm = wg.getRegionManager(p.getWorld());
	    ProtectedCuboidRegion region = new ProtectedCuboidRegion(p.getName().toLowerCase() + "-" + nome, new BlockVector(xma, yme, zma), new BlockVector(xme, yma, zme));
		ApplicableRegionSet set = rm.getApplicableRegions(region);
		DefaultDomain dd = new DefaultDomain();
		
		if (!set.isOwnerOfAll(wg.wrapPlayer(p))) {
			p.sendMessage(Utils.getMensagem("terreno_perto"));
			return;
		}
		
		int tmin = Main.getPlugin().getConfig().getInt("Tamanho.Minimo");
		int tmax = Main.getPlugin().getConfig().getInt("Tamanho.Maximo");
		
		if (tmin > tam) {
			p.sendMessage(getMensagem("tamanho_minimo").replaceAll("(?i)<tamanho>", Integer.toString(tmin)));
			return;
		} else if (tam > tmax) {
			p.sendMessage(getMensagem("tamanho_maximo").replaceAll("(?i)<tamanho>", Integer.toString(tmax)));
			return;
		}
		
		int x = getListaTerrenos(p.getName()).size();
		if (x >= getQuantidadeTerrenos(p) && !isAdmin(p)) {
			p.sendMessage(getMensagem("ter_x_terrenos").replaceAll("(?i)<qtd>", Integer.toString(x)));
			return;
		}
		
		if (hasTerreno(p, nome)) {
			p.sendMessage(getMensagem("terreno_com_esse_nome"));
			return;
		}
		
		if (!Hooks.getEconomy().has(p.getName(), preco)) {
			p.sendMessage(getMensagem("sem_dinheiro").replaceAll("(?i)<money>", Integer.toString(preco)));
			return;
		}
		
	    rm.addRegion(region);
	    region.setPriority(999);
	    dd.addPlayer(wg.wrapPlayer(p));
	    region.setOwners(dd);
        
        try {
			region.setFlag(DefaultFlag.CREEPER_EXPLOSION, DefaultFlag.CREEPER_EXPLOSION.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE, DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.ENDER_BUILD, DefaultFlag.ENDER_BUILD.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.OTHER_EXPLOSION, DefaultFlag.OTHER_EXPLOSION.parseInput(wg, p, "deny"));
			String pvp = "deny";
			if (Main.getPlugin().getConfig().getString("Default_Pvp").equalsIgnoreCase("ativado")) {
				pvp = "allow";
			}
			region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(wg, p, pvp));
			region.setFlag(DefaultFlag.USE, DefaultFlag.USE.parseInput(wg, p, "deny"));
			
			rm.save();
		} catch (InvalidFlagFormat e1) {
			p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
			Utils.logError("Ocorreu um erro ao setar as flags. ");
			e1.printStackTrace();
		} catch (Exception e1) {
			p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
			Utils.logError("Ocorreu um erro ao salvar o terreno. " + region.getId());
			e1.printStackTrace();
		}
        
	    p.teleport(p.getLocation());
        p.sendMessage(Utils.getMensagem("comprou").replaceAll("(?i)<terreno>", nome).replaceAll("(?i)<preco>", Integer.toString(preco)).replaceAll("(?i)<t>", Integer.toString(tam)));
        Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
        Utils.gerarTerreno(p.getLocation(), tam);
        
        try {
			loadSchematic(p.getWorld(), tam, new Vector(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
		} catch (Exception e) {
			p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
			Utils.logError("Ocorreu um erro ao salvar o terreno. " + region.getId());
			e.printStackTrace();
		}
	}
	
	public static void comprarTerreno(Player p, String id, String p2, int preco) {
	    RegionManager rm = wg.getRegionManager(p.getWorld());
	    ProtectedRegion old = rm.getRegion(id);
	    ProtectedCuboidRegion region = new ProtectedCuboidRegion(p.getName().toLowerCase() + "-" + old.getId().split("-")[1], old.getMaximumPoint(), old.getMinimumPoint());
		DefaultDomain dd = new DefaultDomain();
		
		if (hasTerreno(p, old.getId().split("-")[1])) {
			p.sendMessage(Utils.getMensagem("terreno_com_esse_nome_2").replaceAll("(?i)<nome>", old.getId().split("-")[1]));
			return;
		}
		
		int x = getListaTerrenos(p.getName()).size();
		if (x >= getQuantidadeTerrenos(p) && !isAdmin(p)) {
			p.sendMessage(getMensagem("ter_x_terrenos").replaceAll("(?i)<qtd>", Integer.toString(x)));
			return;
		}
		
        Hooks.getEconomy().withdrawPlayer(p.getName(), preco);
		Hooks.getEconomy().depositPlayer(id.split("-")[0], preco);
		p.sendMessage(Utils.getMensagem("comprou_2"));
		p.getWorld().spawn(p.getLocation(), Firework.class);
		
		rm.removeRegion(old.getId());
	    rm.addRegion(region);
	    region.setPriority(999);
	    dd.addPlayer(wg.wrapPlayer(p));
	    region.setOwners(dd);
        
        try {
			region.setFlag(DefaultFlag.CREEPER_EXPLOSION, DefaultFlag.CREEPER_EXPLOSION.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE, DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.ENDER_BUILD, DefaultFlag.ENDER_BUILD.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.OTHER_EXPLOSION, DefaultFlag.OTHER_EXPLOSION.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(wg, p, "deny"));
			region.setFlag(DefaultFlag.USE, DefaultFlag.USE.parseInput(wg, p, "deny"));
			
			rm.save();
		} catch (InvalidFlagFormat e1) {
			p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
			Utils.logError("Ocorreu um erro ao setar as flags. ");
			e1.printStackTrace();
		} catch (Exception e1) {
			p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
			Utils.logError("Ocorreu um erro ao salvar o terreno. " + region.getId());
			e1.printStackTrace();
		}
		
	}
	
	public static boolean hasTerreno(Player p, String nome) {
		return wg.getRegionManager(p.getWorld()).getRegion(p.getName().toLowerCase() + "-" + nome) != null;
	}
	
	public static void sendInfo(Player p) {
	    RegionManager rm = wg.getRegionManager(p.getWorld());
		ApplicableRegionSet set = rm.getApplicableRegions(p.getLocation());
		
		for (ProtectedRegion pr : set) {
			if (pr.getId().contains("-")) {
				if (pr.isOwner(wg.wrapPlayer(p)) || pr.isMember(wg.wrapPlayer(p))) {
					for (String s : FilesManager.getMensagensFile().getStringList("info_2")) {
						p.sendMessage(Utils.format(s)
								.replaceAll("(?i)<pvp>", String.valueOf(pr.getFlag(DefaultFlag.PVP)).replace("DENY", "Desativado").replace("ALLOW", "Ativado"))
								.replaceAll("(?i)<dono>", pr.getId().split("-")[0])
								.replaceAll("(?i)<membros>", pr.getMembers().getPlayers().isEmpty() ? "Nenhum" : pr.getMembers().getPlayers().toString().replaceAll("[\\[|\\]]", ""))
								.replaceAll("(?i)<nome>", pr.getId().split("-")[1])
								.replaceAll("(?i)<comandos>", Utils.getComandosBloqueados(pr.getId().split("-")[0], pr.getId().split("-")[1]).isEmpty() ? "Nenhum" : Utils.getComandosBloqueados(pr.getId().split("-")[0], pr.getId().split("-")[1]).toString().replaceAll("[\\[|\\]]", "")));
					}
					return;
				}
				for (String s : FilesManager.getMensagensFile().getStringList("info_1")) {
					p.sendMessage(Utils.format(s)
							.replaceAll("(?i)<pvp>", String.valueOf(pr.getFlag(DefaultFlag.PVP)).replace("DENY", "Desativado").replace("ALLOW", "Ativado"))
							.replaceAll("(?i)<dono>", pr.getId().split("-")[0])
							.replaceAll("(?i)<membros>", pr.getMembers().getPlayers().isEmpty() ? "Nenhum" : pr.getMembers().getPlayers().toString().replaceAll("[\\[|\\]]", ""))
							.replaceAll("(?i)<nome>", pr.getId().split("-")[1])
							.replaceAll("(?i)<comandos>", Utils.getComandosBloqueados(pr.getId().split("-")[0], pr.getId().split("-")[1]).isEmpty() ? "Nenhum" : Utils.getComandosBloqueados(pr.getId().split("-")[0], pr.getId().split("-")[1]).toString().replaceAll("[\\[|\\]]", "")));
				}
				return;
			}
		}
		
		p.sendMessage(Utils.getMensagem("nenhum_terreno"));
	}
	
	public static Set<String> getListaTerrenos(String player) {
		Set<String> terrenos = new HashSet<>();
		for (World w : Bukkit.getWorlds()) {
			RegionManager rm = wg.getRegionManager(w);
			for (String ab : rm.getRegions().keySet()) {
				if (ab.startsWith(player.toLowerCase() + "-")) {
					terrenos.add(ab.split("-")[1]);
				}
			}
		}
		return terrenos;
	}
	
	public static Set<ProtectedRegion> getTerrenos(String player) {
		Set<ProtectedRegion> terrenos = new HashSet<>();
		for (World w : Bukkit.getWorlds()) {
			RegionManager rm = wg.getRegionManager(w);
			for (String r : rm.getRegions().keySet()) {
				if (r.startsWith(player.toLowerCase())) {
					terrenos.add(wg.getRegionManager(w).getRegion(r));
				}
			}
		}
		
		return terrenos;
	}
	
	public static ProtectedRegion getTerreno(String player, String terreno) {
		for (World w : Bukkit.getWorlds()) {
			ProtectedRegion region = wg.getRegionManager(w).getRegion(player.toLowerCase() + "-" + terreno);
			if (region != null) {
				return region;
			}
		}
		return null;
	}
	
	public static World getTerrenoWorld(String player, String terreno) {
		for (World w : Bukkit.getWorlds()) {
			ProtectedRegion region = wg.getRegionManager(w).getRegion(player.toLowerCase() + "-" + terreno);
			if (region != null) {
				return w;
			}
		}
		return null;
	}
	
	public static Location getTerrenoLocation(String p, String nome) {
		for (World w : Bukkit.getWorlds()) {
			ProtectedRegion a = wg.getRegionManager(w).getRegion(p.toLowerCase() + "-" + nome);
			if (a != null) {
				double x = a.getMinimumPoint().getX() + 1;
				double z = a.getMinimumPoint().getZ();
				return new Location(w, x, w.getHighestBlockYAt((int)x, (int)z), z);
			}
		}
		return null;
	}
	
	public static void addAmigo(Player p, String terreno, String amigo) {
		ProtectedRegion region = TerrenosManager.getTerreno(p.getName(), terreno);
		if (region != null) {
			region.getMembers().addPlayer(amigo);
			try {
				wg.getRegionManager(p.getWorld()).save();
				
			} catch (Exception e1) {
				p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
				Utils.logError("Ocorreu um erro ao salvar o terreno. " + region.getId());
				e1.printStackTrace();
			}
			p.sendMessage(getMensagem("amigo_adicionado"));
		} else {
			p.sendMessage(getMensagem("nao_possui_este_terreno"));
		}
	}
	
	public static void delAmigo(Player p, String terreno, String amigo) {
		ProtectedRegion region = TerrenosManager.getTerreno(p.getName(), terreno);
		if (region != null) {
			region.getMembers().removePlayer(amigo);
			try {
				wg.getRegionManager(p.getWorld()).save();
			} catch (Exception e1) {
				p.sendMessage("§cOcorreu um erro interno, contate um administrador.");
				Utils.logError("Ocorreu um erro ao salvar o terreno. " + region.getId());
				e1.printStackTrace();
			}
			p.sendMessage(getMensagem("amigo_removido"));
		} else {
			p.sendMessage(getMensagem("nao_possui_este_terreno"));
		}
	}
	
	public static File getRandomCasa(int size) {
		List<String> casas = new ArrayList<>();
		Path dir = Paths.get(Main.getPlugin().getDataFolder() + File.separator + "casas" + File.separator);
		if (Files.exists(dir)) {
			try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
				for (Path path : stream) {
					String fn = path.getFileName().toString().split(".schematic")[0];
					if (fn.split("-").length == 2) {
						int smin = Integer.parseInt(fn.split("_")[1].split("-")[0]);
						int smax = Integer.parseInt(fn.split("_")[1].split("-")[1]);
						if (size >= smin && size <= smax) {
							casas.add(path.getFileName().toString());
						}
					}
				}
			} catch (IOException | DirectoryIteratorException e) {
				e.printStackTrace();
			}
			
			if (!casas.isEmpty()) {
				int i = new Random().nextInt(casas.size());
				return new File(dir.toString(), casas.get(i));
			}
		}
		return null;
	}
	
	public static void deletarTerreno(String player, String nome) {
		World w = TerrenosManager.getTerrenoWorld(player, nome);
		RegionManager rm = wg.getRegionManager(w);
		ProtectedRegion terreno = TerrenosManager.getTerreno(player, nome);
		delTerreno(w, terreno.getMinimumPoint(),terreno.getMaximumPoint());
		rm.removeRegion(player.toLowerCase() + "-" + nome);
		FilesManager.getDataFile().set(player.toLowerCase() + "." + nome.toLowerCase(), null);
		FilesManager.saveDataFile();
		try {
			rm.save();
		} catch (Exception e) {
			Utils.logError("Ocorreu um erro ao salvar os terrenos");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void loadSchematic(World world, int size, Vector v) throws Exception {
		if (getRandomCasa(size) == null) {
			return;
		}
		EditSession es = new EditSession(new BukkitWorld(world), Integer.MAX_VALUE);
		CuboidClipboard cc = CuboidClipboard.loadSchematic(getRandomCasa(size));
		cc.paste(es, v, false);
		cc.paste(es, v, false);
	}
	
	public static boolean isInProtectedRegion(Player p) {
		return (wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation()).size() > 0);
	}
	
	public static boolean isInYourRegion(Player p) {
		for (ProtectedRegion pr : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
			return pr.isOwner(wg.wrapPlayer(p));
		}
		return false;
	}
	
	public static String getTerrenoIdByBlock(Block b) {
		for (ProtectedRegion pr : wg.getRegionManager(b.getWorld()).getApplicableRegions(b.getLocation())) {
			if (pr.getId().contains("-")) {
				return pr.getId();
			}
		}
		return "";
	}
	
	public static String getTerrenoNome(Player p) {
		for (ProtectedRegion pr : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
			if (pr.isOwner(wg.wrapPlayer(p)) && pr.getId().contains("-")) {
				return pr.getId().split("-")[1];
			}
		}
		return "";
	}
	
	public static int getTerrenoSize(Player p, String terreno) {
		ProtectedRegion region = getTerreno(p.getName(), terreno);
		return region.getMaximumPoint().getBlockX() - region.getMinimumPoint().getBlockX();
	}
	
	public static void venderTerreno(Player p, int preco) {
		ProtectedRegion region = getTerreno(p.getName(), getTerrenoNome(p));
		
		if (Utils.isaVenda(p.getWorld(), region.getMinimumPoint(), region.getMaximumPoint())) {
			p.sendMessage(Utils.getMensagem("ja_a_venda"));
			return;
		}
		
		Block b = p.getLocation().getBlock();
		b.setType(Material.SIGN_POST);
		Sign s = (Sign) b.getState();
		s.setLine(0, Utils.getMensagem("placa_venda.linha1").replaceAll("(?i)<preco>", Integer.toString(preco)));
        s.setLine(1, Utils.getMensagem("placa_venda.linha2").replaceAll("(?i)<preco>", Integer.toString(preco)));
        s.setLine(2, Utils.getMensagem("placa_venda.linha3").replaceAll("(?i)<preco>", Integer.toString(preco)));
        s.setLine(3, Utils.getMensagem("placa_venda.linha4").replaceAll("(?i)<preco>", Integer.toString(preco)));
        s.update();
        p.sendMessage(Utils.getMensagem("colocado_a_venda").replaceAll("(?i)<terreno>", getTerrenoNome(p)));
	}
	

}
