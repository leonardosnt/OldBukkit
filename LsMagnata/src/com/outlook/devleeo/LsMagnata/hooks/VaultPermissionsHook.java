package com.outlook.devleeo.LsMagnata.hooks;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.outlook.devleeo.LsMagnata.common.SysLogger;

public class VaultPermissionsHook
{

	private static Permission permissionSystem;
	
	public static void setup()
	{
	    RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	    if (permissionProvider != null) {
	    	permissionSystem = permissionProvider.getProvider();
	    	SysLogger.log("Sistema de permissoes: " + permissionSystem.getName());
	    }
	}
	
	public static Permission getPermissionSystem()
	{
		return permissionSystem;
	}
}
