package com.outlook.devleeo.LsMagnata.threads;

import java.util.HashSet;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.common.SysLogger;
import com.outlook.devleeo.LsMagnata.hooks.VaultPermissionsHook;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class UpdatePermissionsThread extends Thread
{

	@Override
	public void run()
	{
		long start = 0;
		boolean debug = LsMagnata.getPlugin().getConfig().getBoolean("Settings.DebugConsole");
		
		if (debug)
		{
			start = System.currentTimeMillis();
			SysLogger.log("Checando permissions...");
		}
		
		HashSet<String> magnataPermissions = new HashSet<>(LsMagnata.getPlugin().getConfig().getStringList("MagnataPermissions"));
		Permission permSystem = VaultPermissionsHook.getPermissionSystem();
		
		for (OfflinePlayer offline : Bukkit.getOfflinePlayers())
		{
			if (offline.getLastPlayed() > (System.currentTimeMillis() - 259200000))
			{
				if (offline.getName().equalsIgnoreCase(MagnataManager.getCurrentMagnata()))
				{
					for (String perm : magnataPermissions)
					{
						for (World world : Bukkit.getWorlds())
						{
							if (!permSystem.has(world, offline.getName(), perm))
							{
								permSystem.playerAdd(world, offline.getName(), perm);
							}
						}
					}
				}
				else
				{
					for (String perm : magnataPermissions)
					{
						for (World world : Bukkit.getWorlds())
						{
							if (permSystem.has(world, offline.getName(), perm))
							{
								permSystem.playerRemove(world, offline.getName(), perm);
							}
						}
					}
				}
			}
		}
		
		if (debug)
		{
			SysLogger.log("Checagem finalizada ("+(System.currentTimeMillis() - start)+"ms)");
		}
	}
}
