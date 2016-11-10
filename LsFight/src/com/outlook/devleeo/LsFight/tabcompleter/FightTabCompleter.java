package com.outlook.devleeo.LsFight.tabcompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class FightTabCompleter implements TabCompleter {
	
	private static List<String> completationsSemPerm;
	private static List<String> completationsComPerm;
	
	static {
		ImmutableList.Builder<String> ilist = ImmutableList.<String>builder();
		
		ilist.add("Participar");
		ilist.add("Status");
		ilist.add("Finiciar");
		ilist.add("Ffinalizar");
		ilist.add("Setentrada");
		ilist.add("Setsaida");
		ilist.add("Setpos1");
		ilist.add("Setpos2");
		
		completationsComPerm = ilist.build();
		
		ImmutableList.Builder<String> ilist2 = ImmutableList.<String>builder();
		
		ilist2.add("Participar");
		ilist2.add("Status");
		
		completationsSemPerm = ilist2.build();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (sender.hasPermission("lsfight.admin")) {
			if (args.length == 1) {
				return StringUtil.copyPartialMatches(args[0], completationsComPerm, new ArrayList<String>(completationsComPerm.size()));
			}
		} else {
			if (args.length == 1) {
				return StringUtil.copyPartialMatches(args[0], completationsSemPerm, new ArrayList<String>(completationsSemPerm.size()));
			}
		}
		return ImmutableList.of();
	}

}
