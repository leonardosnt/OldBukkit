package com.outlook.devleeo.LsMagnata.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class MagnataCommand implements CommandExecutor
{
	
	@Override
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args)
	{

		if (args.length == 1 && args[0].equalsIgnoreCase("info"))
		{
			sender.sendMessage(LsMagnata.formatString("<prefix>&aInformações"));
			sender.sendMessage(" §aCriador: §fDevLeeo");
			sender.sendMessage(" §aVersão atual: §f" + LsMagnata.getPlugin().getDescription().getVersion());
			return false;
		}
		
		if (StringUtils.isBlank(LsMagnata.getDatabaseFile().getYmlFile().getString("current")))
		{
			sender.sendMessage(LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Messages.None.Message")));
		}
		else
		{
			Player player = Bukkit.getPlayer(MagnataManager.getCurrentMagnata());
			sender.sendMessage(LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Messages.Current.Message")).replaceAll("(?i)<player>", player == null ? MagnataManager.getCurrentMagnata() : player.getName()));
		}
		
		return false;
	}

}
