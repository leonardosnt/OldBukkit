package com.outlook.devleeo.LsMito.hooks;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultPermissionsHook
{

	private static Permission permissionSystem;

	public static void setup()
	{
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
		{
			permissionSystem = permissionProvider.getProvider();
			System.out.println("Vault hooked (Permissions)");
		}
	}

	public static Permission getPermissionSystem()
	{
		return permissionSystem;
	}
}
