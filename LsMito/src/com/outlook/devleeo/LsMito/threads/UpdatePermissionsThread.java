package com.outlook.devleeo.LsMito.threads;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.outlook.devleeo.LsMito.api.LsMito;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;
import com.outlook.devleeo.LsMito.hooks.VaultPermissionsHook;

public class UpdatePermissionsThread extends Thread
{
	public static long lastCheck;
	
	@Override
	public void run()
	{
		long start = System.currentTimeMillis();
		System.out.println("[LsMito] Atualizando permissoes do mito...");
		
		Permission permSystem = VaultPermissionsHook.getPermissionSystem();
		
		for (OfflinePlayer offline : Bukkit.getOfflinePlayers())
		{
			if (offline.getLastPlayed() > (System.currentTimeMillis() - 259200000) || offline.isOnline())
			{
				
				for (String perm : ConfigDefaults.mitoPermissions)
				{
					for (World world : Bukkit.getWorlds())
					{
						if (LsMito.isMito(offline.getName()))
						{
							if (!permSystem.has(world, offline.getName(), perm))
							{
								permSystem.playerAdd(world, offline.getName(), perm);
							}
								
						}
						else
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
		
		lastCheck = (System.currentTimeMillis() / 1000);
		System.out.println("[LsMito] Permissoes do mito atualizadas (" + (System.currentTimeMillis() - start)+ "ms)");
	}
}
