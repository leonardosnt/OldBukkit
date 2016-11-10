package com.outlook.devleeo.LsMvP.tabcompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class MvpTabCompleter implements TabCompleter {
	
	private static String[] completations = {"Participar", "Status", "Setspawnboss", "Setentrada", "Setsaida", "Finiciar","Ffinalizar"};
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			if (sender.hasPermission("lsmvp.admin")) {
				return StringUtil.copyPartialMatches(args[0], Arrays.asList(completations), new ArrayList<String>(completations.length));
			} else {
				return StringUtil.copyPartialMatches(args[0], Arrays.asList(completations[0], completations[1]), new ArrayList<String>(completations.length));
			}
		}
		return ImmutableList.of();
		
	}
}
