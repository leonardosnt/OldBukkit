package com.outlook.devleeo.LsBans.managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsBans.LsBans;
import com.outlook.devleeo.LsBans.outros.IpHistory;

public class BanManager {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	public static Plugin plugin;
	
	public static void banPlayer(String nome, int tempo, String admin, String motivo) {
		if (isBanido(nome)) {
			unbanPlayer(nome);
		}
		try(PreparedStatement ps = LsBans.con.prepareStatement("INSERT INTO " + LsBans.tabela + "(nick, currentTime, tempo, motivo, admin, banido, ip) VALUES (?,?,?,?,?,?,?)");) {
			ps.setString(1, nome.toLowerCase());
			ps.setLong(2, (System.currentTimeMillis() / 1000 / 60));
			ps.setInt(3, tempo);
			ps.setString(4, motivo);
			ps.setString(5, admin);
			ps.setBoolean(6, true);
			ps.setString(7, IpHistory.getIp(nome));
			ps.executeUpdate();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao banir o jogador "+nome+" Erro: ");
			e.printStackTrace();
		}
		
		Player target = Bukkit.getPlayer(nome);
		
		if (tempo == 0) {
			if (config.getBoolean("Ban.Enviar_Mensagem_Global")) {
				Bukkit.broadcastMessage(MensagensManager.getMensagem("Ban.Global.Permanente").replace("<motivo>", motivo).replace("<admin>", admin).replace("<player>", nome));
			}
			if (target != null) {
				target.kickPlayer(MensagensManager.getMensagem("Ban.Permanente").replace("<motivo>", motivo).replace("<admin>", admin));
			}
		} else {
			if (config.getBoolean("Ban.Enviar_Mensagem_Global")) {
				Bukkit.broadcastMessage(MensagensManager.getMensagem("Ban.Global.Temporario").replace("<motivo>", motivo).replace("<admin>", admin).replace("<tempo>", Integer.toString(tempo)).replace("<player>", nome));
			}
			if (target != null) {
				target.kickPlayer(MensagensManager.getMensagem("Ban.Temporario").replace("<motivo>", motivo).replace("<admin>", admin).replace("<tempo>", Integer.toString(tempo)));
			}
		}
		
	}
	
	public static boolean isBanido(String player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT * FROM "+LsBans.tabela+" WHERE nick='"+player.toLowerCase()+"' AND banido=1")) {
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao verficar se o jogador "+player+" esta banido. Erro: ");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void unbanPlayer(String nome) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("UPDATE "+LsBans.tabela+" SET banido=0 WHERE nick='"+nome.toLowerCase()+"'")) {
			ps.executeUpdate();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao desbanir o jogador "+nome+" Erro: ");
			e.printStackTrace();
		}
	}
	
	public static String getBan(String player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT currentTime, tempo, motivo, admin FROM "+LsBans.tabela+" WHERE nick='"+player.toLowerCase()+"' AND banido=1")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong("currentTime")+";"+rs.getInt("tempo")+";"+rs.getString("motivo")+";"+rs.getString("admin");
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar pegar informacoes sobre o ban de " + player);
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isIpBanido(String ip) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT ip FROM "+LsBans.tabela+" WHERE ip='"+ip+"' AND banido=1")) {
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar verificar se o ip " + ip + " esta banido.");
			e.printStackTrace();
		}
		return false;
	}
	
	public static void banIp(String ip) {
		if (isIpBanido(ip)) {
			unbanIp(ip);
		}
		try (PreparedStatement ps = LsBans.con.prepareStatement("INSERT INTO "+LsBans.tabela+"(nick, admin, banido, ip) VALUES (?,?,?,?)")) {
			ps.setString(1, "IP_BAN");
			ps.setString(2, "IP_BAN");
			ps.setBoolean(3, true);
			ps.setString(4, ip);
			ps.executeUpdate();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar banir o ip " + ip);
			e.printStackTrace();
		}
	}
	
	public static void unbanIp(String ip) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("DELETE FROM "+LsBans.tabela+" WHERE ip='"+ip+"'")) {
			ps.executeUpdate();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar desbanir o ip " + ip);
			e.printStackTrace();
		}
	}
	
	public static int getBans(String player) {
		List<String> qtd = new ArrayList<>();
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT nick FROM "+LsBans.tabela+" WHERE nick='"+player.toLowerCase()+"'")) {
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return -1;
			} else {
				while (rs.next()) {
					qtd.add(rs.getString(1));
				}
				rs.close();
				return qtd.size()+1;
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar pegar o numero de bans de " + player);
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void getLastBans(CommandSender sender, String player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT motivo, admin FROM "+LsBans.tabela+" WHERE nick='"+player.toLowerCase()+"' ORDER BY currentTime DESC LIMIT 5")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sender.sendMessage("   §cMotivo: §7"+rs.getString("motivo") + " §cBanido por: §7" + rs.getString("admin"));
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao tentar pegar os ultimos bans de " + player);
			e.printStackTrace();
		}
	}
	
	public static void gerarHistorico(String player) {
		Path cbfolder = Paths.get(plugin.getDataFolder() + File.separator + "checkbans");
		
		if (!Files.exists(cbfolder)) {
			try {
				Files.createDirectory(cbfolder);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar o diretorio /checkbans");
				e.printStackTrace();
			}
		}
		
		File readMe = new File(cbfolder + File.separator + "_LEIA-ME.txt");
		if (!readMe.exists()) {
			try {
				readMe.createNewFile();
				PrintWriter pw = new PrintWriter(new FileWriter(readMe, true));
				pw.print("Atenção, isso gera historicos temporarios, toda vez que o plugin é desabilitado TODOS os historicos são deletados.");
				pw.close();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar o arquivo LEIA-ME.txt");
				e.printStackTrace();
			}
		}
		
		File playerHistory = new File(cbfolder + File.separator + "Historico de "+player.toLowerCase()+".txt");
		try {
			playerHistory.delete();
			playerHistory.createNewFile();
			PrintWriter pw = new PrintWriter(new FileWriter(playerHistory, true));
			pw.println("# Historico de TODOS os bans #");
			pw.println("# Motivo |  Tempo | Banido por #");
			pw.println("");
			try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT motivo, tempo, admin FROM "+LsBans.tabela+" WHERE nick='"+player.toLowerCase()+"'")) {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					pw.println(rs.getString("motivo") + " | " + rs.getInt("tempo") + " | " + rs.getString("admin"));
				}
				rs.close();
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar o historico de " + player + " Erro:");
				e.printStackTrace();
			}
			pw.close();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§4[LsBans] §cOcorreu um erro ao criar o historico de " + player + " Erro:");
			e.printStackTrace();
		}
	}
}
