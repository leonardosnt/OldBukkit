package com.outlook.devleeo.LsMito.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsMito.LsMitoPlugin;
import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;

public class MitoCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 1)
		{
			if (args[0].matches("(?i)\\?|help|autor|criador"))
			{
				sender.sendMessage("§5[LsMito] §cVersão " + LsMitoPlugin.getPlugin().getDescription().getVersion());
				sender.sendMessage("§5[LsMito] §cCriador: DevLeeo");
			}
			else if (args[0].equalsIgnoreCase("reload"))
			{
				if (sender.hasPermission("lsmito.reload"))
				{
					if (!new File(LsMitoPlugin.getPlugin().getDataFolder(), "config.yml").exists())
					{
						LsMitoPlugin.getPlugin().saveDefaultConfig();
					}
					LsMitoPlugin.getPlugin().reloadConfig();
					ConfigDefaults.loadDefaults();
					sender.sendMessage("§5[LsMito] §cConfig recarregada.");
				}
				else
				{
					sender.sendMessage(ConfigDefaults.notEnoughPermissions);
				}
			}
			return false;
		}
		
		String currentMito = LsMito.getMito();
		sender.sendMessage(currentMito == null ? ConfigDefaults.noneMitoMesage : ConfigDefaults.currentMitoMessage.replaceAll("(?i)\\{player\\}", currentMito));
		return false;
	}

}
