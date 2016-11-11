package com.outlook.devleeo.LsBans.outros;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.outlook.devleeo.LsBans.LsBans;

public class IpHistory {
	
	public static void saveIp(Player player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("INSERT INTO " + LsBans.tabelaIps + "(nick, ip) VALUES (?,?)")) {
			ps.setString(1, player.getName().toLowerCase());
			ps.setString(2, player.getAddress().getHostName().toString());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getIp(String player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("SELECT ip FROM " + LsBans.tabelaIps + " WHERE nick='"+player.toLowerCase()+"'")) {
			ResultSet rs =  ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updateIp(Player player) {
		try (PreparedStatement ps = LsBans.con.prepareStatement("UPDATE " + LsBans.tabelaIps + " SET nick='"+player.getName().toLowerCase()+"', ip='"+player.getAddress().getHostName().toString()+"' WHERE nick='"+player.getName().toLowerCase()+"'")) {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
