package com.outlook.devleeo.LsPin.tabCompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.outlook.devleeo.LsPin.filesmanager.Config;
import com.outlook.devleeo.LsPin.utils.Utils;

public class PinTabCompleter implements TabCompleter {

	private static List<String> c1 = new ArrayList<>();
	private static List<String> c2 = new ArrayList<>();
	
	static {
		c1.add("Ativar");
		c1.add("Recuperar");
		if (Config.ATIVAR_PIN_ATUAL) {
			c1.add("Atual");
		}
		
		c2.add("Ativar");
		c2.add("Recuperar");
		c2.add("Atual");
		c2.add("Remover");
		c2.add("Ver");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (Utils.isAuthorized(sender)) {
			if (args.length == 1) {
				return StringUtil.copyPartialMatches(args[0], c2, new ArrayList<String>(c2.size()));
			}
		} else {
			if (args.length == 1) {
				return StringUtil.copyPartialMatches(args[0], c1, new ArrayList<String>(c1.size()));
			}
		}
	
		return null;
	}
}
