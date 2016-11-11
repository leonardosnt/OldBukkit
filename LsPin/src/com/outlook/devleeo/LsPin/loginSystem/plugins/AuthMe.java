package com.outlook.devleeo.LsPin.loginSystem.plugins;

import org.bukkit.Bukkit;

import com.outlook.devleeo.LsPin.LsPin;
import com.outlook.devleeo.LsPin.loginSystem.LoginSystem;

public class AuthMe {

	public static void hook() {
		if (Bukkit.getPluginManager().getPlugin("AuthMe") != null) {
			if (Bukkit.getPluginManager().getPlugin("AuthMe").getDescription().getMain().equals("uk.org.whoami.authme.AuthMe")) {
				Bukkit.getConsoleSender().sendMessage("§3[LsPin] §bAuthMe encontrado, LoginSystem = AuthMe1");
				LsPin.login = LoginSystem.AuthMe1;
			}
		}
		
	}
	
}
