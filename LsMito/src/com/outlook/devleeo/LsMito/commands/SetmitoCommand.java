package com.outlook.devleeo.LsMito.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;

public class SetmitoCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender.hasPermission("lsmito.setmito"))
		{
			if (args.length == 0)
			{
				sender.sendMessage("§5[LsMito] §cUse /setmito <player>");
			}
			else
			{
				LsMito.setMito(args[0]);
			}
		}
		else
		{
			sender.sendMessage(ConfigDefaults.notEnoughPermissions);
		}
		return false;
	}

}
