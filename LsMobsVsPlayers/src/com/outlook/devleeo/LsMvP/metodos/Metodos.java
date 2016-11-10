package com.outlook.devleeo.LsMvP.metodos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.outlook.devleeo.LsMvP.Main;
import com.outlook.devleeo.LsMvP.files.Files;
import com.outlook.devleeo.LsMvP.outros.Hooks;

public class Metodos {
	
	private static boolean isIniciado = false;
	private static boolean isAcontecendo = false;
	public static ArrayList<Player> participantes = new ArrayList<>();
	private static ArrayList<Entity> mobsSpawnados = new ArrayList<>();
	private static Files files = Files.getInstance();
	public static Entity boss = null;

	public static void setAcontecendo(boolean acontecendo) {
		isAcontecendo = acontecendo;
	}
	
	public static void setIniciado(boolean iniciado) {
		isIniciado = iniciado;
	}
	
	public static boolean isAcontecendo() {
		return isAcontecendo;
	}
	
	public static boolean isIniciado() {
		return isIniciado;
	}
	
	public static int getBossHealth() {
		if (boss == null) {
			return 0;
		}
		return ((Giant) boss).getHealth();
	}
	
	public static void spawnBossAndMobs() {
		boss = Bukkit.getWorld(files.getData().getString("SpawnMob.World")).spawnEntity(getSpawnMob(), EntityType.GIANT);
		((Giant)boss).setMaxHealth(Main.getInstance().getConfig().getInt("BossHealth"));
		((Giant)boss).setHealth((((Giant) boss).getMaxHealth()));
		((Giant)boss).setCustomNameVisible(true);
		((Giant)Metodos.boss).setCustomName(Main.getInstance().getConfig().getString("BossName").replace("&", "§").replace("{vida}", String.valueOf(((Giant) Metodos.boss).getHealth())).replace("<3", "♥").replace("{max}", String.valueOf(Main.getInstance().getConfig().getInt("BossHealth"))));
		mobsSpawnados.add(boss);
				
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				int num = new Random().nextInt(6);
				
				switch (num) {
				
				case 0:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Zombie.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Zombie.Quantidade"); q++) {
							Damageable zombie = boss.getWorld().spawn(boss.getLocation(), Zombie.class);
							mobsSpawnados.add(zombie);
						}
					}break;
				case 1:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Blaze.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Blaze.Quantidade"); q++) {
							Damageable blaze = boss.getWorld().spawn(boss.getLocation(), Blaze.class);
							mobsSpawnados.add(blaze);
						}
					}break;
				case 2:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Skeleton.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Skeleton.Quantidade"); q++) {
							Damageable skeleton = boss.getWorld().spawn(boss.getLocation(), Skeleton.class);
							mobsSpawnados.add(skeleton);
						}
					} break;
				case 3:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Witch.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Witch.Quantidade"); q++) {
							Damageable witch = boss.getWorld().spawn(boss.getLocation(), Witch.class);
							mobsSpawnados.add(witch);
						}
					} break;
				case 4:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Spider.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Spider.Quantidade"); q++) {
							Damageable spider = boss.getWorld().spawn(boss.getLocation(), Spider.class);
							mobsSpawnados.add(spider);
						}
					} break;
				case 5:
					if (Main.getInstance().getConfig().getBoolean("Mobs.PigZombie.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.PigZombie.Quantidade"); q++) {
							Damageable pigzombie = boss.getWorld().spawn(boss.getLocation(), PigZombie.class);
							mobsSpawnados.add(pigzombie);
						}
					} break;
				case 6:
					if (Main.getInstance().getConfig().getBoolean("Mobs.Slime.Spawnar")) {
						for(int q = 0; q < Main.getInstance().getConfig().getInt("Mobs.Slime.Quantidade"); q++) {
							Damageable slime = boss.getWorld().spawn(boss.getLocation(), Slime.class);
							mobsSpawnados.add(slime);
						}
					} break;

				default:
					break;
				}
				
			}
		}, 0, Main.getInstance().getConfig().getInt("Mobs.Delay") * 20);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (Main.getInstance().getConfig().getBoolean("SpawnarCapetinhas.Ativar")) {
					for (int i = 0; i < Main.getInstance().getConfig().getInt("SpawnarCapetinhas.Quantidade"); i++) {
						Zombie capetinha = boss.getWorld().spawn(boss.getLocation(), Zombie.class);
						capetinha.setBaby(true);
						capetinha.setCustomNameVisible(true);
						capetinha.setCustomName("§4Capetinha");
						capetinha.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30000, 4));
						capetinha.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30000, 3));
						capetinha.setMaxHealth(100);
						capetinha.setHealth(capetinha.getMaxHealth());
						mobsSpawnados.add(capetinha);
					}
				}
				
			}
		}.runTaskLater(Main.getInstance(),10 * 20);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (participantes.size() == 0) return;
				Player random = participantes.get(new Random().nextInt(participantes.size()));
				((Giant) boss).setTarget(random);
			}
		}, 0, 10 * 20);
		
	}
	
	public static void setSpawnBoss(Player p) {
		files.getData().set("SpawnMob." + "World", p.getWorld().getName());
		files.getData().set("SpawnMob." + "X", p.getLocation().getX());
		files.getData().set("SpawnMob." + "Y", p.getLocation().getY());
		files.getData().set("SpawnMob." + "Z", p.getLocation().getZ());
		files.getData().set("SpawnMob." + "YAW", p.getLocation().getYaw());
		files.getData().set("SpawnMob." + "PITCH", p.getLocation().getPitch());
		files.saveData();
	}
	
	public static void setEntrada(Player p) {
		files.getData().set("Entrada." + "World", p.getWorld().getName());
		files.getData().set("Entrada." + "X", p.getLocation().getX());
		files.getData().set("Entrada." + "Y", p.getLocation().getY());
		files.getData().set("Entrada." + "Z", p.getLocation().getZ());
		files.getData().set("Entrada." + "YAW", p.getLocation().getYaw());
		files.getData().set("Entrada." + "PITCH", p.getLocation().getPitch());
		files.saveData();
	}
	
	public static void setSaida(Player p) {
		files.getData().set("Saida." + "World", p.getWorld().getName());
		files.getData().set("Saida." + "X", p.getLocation().getX());
		files.getData().set("Saida." + "Y", p.getLocation().getY());
		files.getData().set("Saida." + "Z", p.getLocation().getZ());
		files.getData().set("Saida." + "YAW", p.getLocation().getYaw());
		files.getData().set("Saida." + "PITCH", p.getLocation().getPitch());
		files.saveData();
	}
	
	public static Location getSpawnMob() {
		if (files.getData().getString("SpawnMob") == null) return null;
		
		World w = Bukkit.getWorld(files.getData().getString("SpawnMob.World"));
		double x = files.getData().getDouble("SpawnMob.X");
		double y = files.getData().getDouble("SpawnMob.Y");
		double z = files.getData().getDouble("SpawnMob.Z");
		float pit = (float) files.getData().getDouble("SpawnMob.YAW");
		float yaw = (float) files.getData().getDouble("SpawnMob.PITCH");
		
		Location loc = new Location(w, x, y, z);
		
		loc.setPitch(pit);
		loc.setYaw(yaw);
		
		return loc;
	}
	
	public static Location getSaida() {
		
		if (files.getData().getString("Saida") == null)return null;
		
		World w = Bukkit.getWorld(files.getData().getString("Saida.World"));
		double x = files.getData().getDouble("Saida.X");
		double y = files.getData().getDouble("Saida.Y");
		double z = files.getData().getDouble("Saida.Z");
		float pit = (float) files.getData().getDouble("Entrada.PITCH");
		float yaw = (float) files.getData().getDouble("Entrada.YAW");
		
		return new Location(w, x, y, z, yaw, pit);
	}
	
	public static Location getEntrada() {
		
		if (files.getData().getString("Entrada") == null)return null;
		
		World w = Bukkit.getWorld(files.getData().getString("Entrada.World"));
		double x = files.getData().getDouble("Entrada.X");
		double y = files.getData().getDouble("Entrada.Y");
		double z = files.getData().getDouble("Entrada.Z");
		float pit = (float) files.getData().getDouble("Entrada.PITCH");
		float yaw = (float) files.getData().getDouble("Entrada.YAW");
		
		return new Location(w, x, y, z, yaw, pit);
	}
	
	public static void teleportSaida(Player p) {
		p.teleport(getSaida());
	}
	
	public static void teleportEntrada(Player p) {
		p.teleport(getEntrada());
	}
	
	public static boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage("§cEste comando nao pode ser executado pelo console");
			return false;
		}
	}
	
	public static boolean isAuthorized(CommandSender sender) {
		if (sender.hasPermission("lsmvp.admin")) {
			return true;
		} else {
			sender.sendMessage(formatMessage("{prefix} &cVocê não tem acesso a este comando."));
			return false;
		}
	}
	
	public static void finalizarMvP() {
		setAcontecendo(false);
		setIniciado(false);
		
		for(Player p : participantes) {
			teleportSaida(p);
		}
		
		for(Entity mob : mobsSpawnados) {
			((Damageable) mob).setHealth(0);
		}
		
		participantes.clear();
		mobsSpawnados.clear();
		Bukkit.getScheduler().cancelTasks(Main.getInstance());
	}
	
	public static String formatMessage(String message) {
		String messageFormated = message
				.replace("&", "§")
				.replaceAll("(?i)([vV])oce", "$1ocê")
			    .replaceAll("(?i)([nN])ao", "$1ão")
			    .replaceAll("(?i)([nN])inguem", "$1inguém")
			    .replaceAll("(?i)([nN])umero", "$1úmero")
			    .replaceAll("(?i)([jJ])a", "$1á")
			    .replaceAll("(?i)([sS])era", "$1erá")
				.replace("{prefix}", Main.getInstance().getConfig().getString("Prefix").replace("&", "§"))
				.replace("%participantes%", String.valueOf(participantes.size()))
				.replace("%>>%", "»");
		
		return messageFormated;
	}
	
	public static boolean checkParticipantes() {
		if (participantes.size() >= Main.getInstance().getConfig().getInt("MinimoParticipantes")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void preparar(final int avisos) {
		int tempoEntreAvisos = Main.getInstance().getConfig().getInt("Iniciando.TempoEntreAvisos");
		if (avisos == 0) {
			iniciarMvP();
		} else {
			for (String s : Main.getInstance().getConfig().getStringList("Iniciando.Mensagem")) {
				Bukkit.broadcastMessage(formatMessage(s).replace("%seg%", Integer.toString(avisos * tempoEntreAvisos)));
			}
			new BukkitRunnable() {
				
				@Override
				public void run() {
					preparar(avisos - 1);
				}
				
			}.runTaskLater(Main.getInstance(), tempoEntreAvisos * 20);
		}
	}
	
	public static Enchantment getEnchantByName(String name) {
		
		switch (name.toLowerCase()) {
		case "power":
			return Enchantment.ARROW_DAMAGE;
			
		case "flame":
			return Enchantment.ARROW_FIRE;
			
		case "infinity":
			return Enchantment.ARROW_INFINITE;
			
		case "punch":
			return Enchantment.ARROW_KNOCKBACK;
		
		case "sharpness":
			return Enchantment.DAMAGE_ALL;
		
		case "baneofarthropods":
			return Enchantment.DAMAGE_ARTHROPODS;
		
		case "smite":
			return Enchantment.DAMAGE_UNDEAD;
		
		case "efficiency":
			return Enchantment.DIG_SPEED;
		
		case "unbreaking":
			return Enchantment.DURABILITY;
		
		case "fireaspect":
			return Enchantment.FIRE_ASPECT;
		
		case "knockback":
			return Enchantment.KNOCKBACK;
		
		case "looting":
			return Enchantment.LOOT_BONUS_MOBS;
		
		case "fortune":
			return Enchantment.LOOT_BONUS_BLOCKS;
		
		case "respiration":
			return Enchantment.OXYGEN;
		
		case "protection":
			return Enchantment.PROTECTION_ENVIRONMENTAL;
		
		case "blastprotection":
			return Enchantment.PROTECTION_EXPLOSIONS;
		
		case "projectileprotection":
			return Enchantment.PROTECTION_PROJECTILE;
		
		case "featherfalling":
			return Enchantment.PROTECTION_FALL;
		
		case "fireprotection":
			return Enchantment.PROTECTION_FIRE;
		
		case "silktouch":
			return Enchantment.SILK_TOUCH;
		
		case "thorns":
			return Enchantment.THORNS;
		
		case "aquaafinity":
			return Enchantment.WATER_WORKER;
		
		default: 
			return null;
		}
	}
	
	public static void iniciarMvP() {
		
		if (checkParticipantes()) {
			spawnBossAndMobs();
			setIniciado(true);
			
			for(String msg : Main.getInstance().getConfig().getStringList("EventoIniciado")) {
				Bukkit.broadcastMessage(formatMessage(msg));
			}
		} else {
			
			for(String msg : Main.getInstance().getConfig().getStringList("MinParticipantes")) {
				Bukkit.broadcastMessage(formatMessage(msg));
			}

			finalizarMvP();
			
		}
		
	}
	
	public static void setGanhador(final Player p) {
		
		for(String msg : Main.getInstance().getConfig().getStringList("EventoFinalizado")) {
			msg = formatMessage(msg).replace("{ganhador}", p.getName()).replace("{tag}", Main.getInstance().getConfig().getString("Tag.Formato").replace("&", "§"));
			Bukkit.broadcastMessage(msg);
		} 
		
		Hooks.eco.depositPlayer(p.getName(), Main.getInstance().getConfig().getInt("Premios.Dinheiro"));
		
		files.getData().set("Vencedor", p.getName());
		files.saveData();
		
		for(Player p2 : participantes) {
			if (participantes.size() > 0) {
				p2.sendMessage(formatMessage(Main.getInstance().getConfig().getString("Finalizando")));
			}
		}
		
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				finalizarMvP();
				
			}
		}, 10 * 20);
		
		if (Main.getInstance().getConfig().getStringList("Premios.Items") == null)return;
		
		if (Main.getInstance().getConfig().getBoolean("Premios.ItemsRandomicos")) {
			
			List<String> itens = Main.getInstance().getConfig().getStringList("Premios.Items");
			Random r = new Random();
			String lista = itens.get(r.nextInt(itens.size()));
			
			ItemStack i;
			String[] item = lista.split(" ");
			String[] item2 = item[0].split(":");

			if (item.length == 2) {
				if (item2.length > 1) {
					i = new ItemStack(Integer.parseInt(item2[0]), Integer.parseInt(item[1]), Byte.parseByte(item2[1]));
					p.getInventory().addItem(i);
				} else {
					i = new ItemStack(Integer.parseInt(item2[0]), Integer.parseInt(item[1]));
					p.getInventory().addItem(i);
				}
			} else if (item.length == 3) {
				try {
					if (item[2].contains("nome")) {
						i = new ItemStack(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
						
						String[] nome = item[2].split(":");
						ItemMeta data = i.getItemMeta();
						
						data.setDisplayName(nome[1].replace("&", "§").replace("_", " "));
						i.setItemMeta(data);
						p.getInventory().addItem(i);
					} else {
						i = new ItemStack(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
		        		for (int in = 2; in <= item.length - 1; in++) {
							String[] enchant = item[in].split(":");
							i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
						}
		        		p.getInventory().addItem(i);
					}
				} catch(ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e){
					Bukkit.getConsoleSender().sendMessage("§4[MvP] §cOs items nao estao configurados corretamente.");
				}
			} else if (item.length >= 4) {
				try {
					if (item[2].contains("nome")) {
						
						i = new ItemStack(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
						String[] nome = item[2].split(":");
						ItemMeta data = i.getItemMeta();
						
						data.setDisplayName(nome[1].replace("&", "§").replace("_", " "));
						i.setItemMeta(data);
						
		        		for (int in = 3; in <= item.length - 1; in++) {
							String[] enchant = item[in].split(":");
							i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
						}
						p.getInventory().addItem(i);
						
					} else {
						i = new ItemStack(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
		        		for (int in = 2; in <= item.length - 1; in++) {
							String[] enchant = item[in].split(":");
							i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
						}
		        		p.getInventory().addItem(i);
					}
				} catch(ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e){
					Bukkit.getConsoleSender().sendMessage("§4[MvP] §cOs items nao estao configurados corretamente.");
				}
			}
		} else {
			
			for(String lista : Main.getInstance().getConfig().getStringList("Premios.Items")) {//inicio items
				
				ItemStack i;
				String[] item = lista.split(" ");
				String[] item2 = item[0].split(":");

				if (item.length == 2) {
					if (item2.length > 1) {
						i = new ItemStack(Integer.parseInt(item2[0]), Integer.parseInt(item[1]), Byte.parseByte(item2[1]));
						p.getInventory().addItem(i);
					} else {
						i = new ItemStack(Integer.parseInt(item2[0]), Integer.parseInt(item[1]));
						p.getInventory().addItem(i);
					}
				} else if (item.length == 3) {
					try {
						if (item[2].contains("nome")) {
							i = new ItemStack(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
							
							String[] nome = item[2].split(":");
							ItemMeta data = i.getItemMeta();
							
							data.setDisplayName(nome[1].replace("&", "§").replace("_", " "));
							i.setItemMeta(data);
							p.getInventory().addItem(i);
						} else {
							i = new ItemStack(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
		            		for (int in = 2; in <= item.length - 1; in++) {
								String[] enchant = item[in].split(":");
								i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
							}
		            		p.getInventory().addItem(i);
						}
					} catch(ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e){
						Bukkit.getConsoleSender().sendMessage("§4[MvP] §cOs items nao estao configurados corretamente.");
					}
				} else if (item.length >= 4) {
					try {
						if (item[2].contains("nome")) {
							
							i = new ItemStack(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
							String[] nome = item[2].split(":");
							ItemMeta data = i.getItemMeta();
							
							data.setDisplayName(nome[1].replace("&", "§").replace("_", " "));
							i.setItemMeta(data);
							
		            		for (int in = 3; in <= item.length - 1; in++) {
								String[] enchant = item[in].split(":");
								i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
							}
							p.getInventory().addItem(i);
							
						} else {
							i = new ItemStack(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
		            		for (int in = 2; in <= item.length - 1; in++) {
								String[] enchant = item[in].split(":");
								i.addUnsafeEnchantment(getEnchantByName(enchant[0]), Integer.parseInt(enchant[1]));
							}
		            		p.getInventory().addItem(i);
						}
					} catch(ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e){
						Bukkit.getConsoleSender().sendMessage("§4[MvP] §cOs items nao estao configurados corretamente.");
					}
				}
			}//fim items
			
		}
		
	}
	
}
