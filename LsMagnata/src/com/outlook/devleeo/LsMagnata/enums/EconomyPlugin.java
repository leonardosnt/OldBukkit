package com.outlook.devleeo.LsMagnata.enums;

import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsMagnata.LsMagnata;

public enum EconomyPlugin
{
	FE_ECONOMY("org.melonbrew.fe.API", "getTopAccounts", new Class[0], LsMagnata.getPluginWithNotExactName("Fe")), ICONOMY("com.iCo6.system.Queried", "topAccounts", new Class[] {int.class}, LsMagnata.getPluginWithNotExactName("iConomy")),
	CRAFTECONOMY3(null, null, null, null), ESSENTIALS_ECO(null, null, null, null);
	
	private String methodClass;
	private String methodName;
	private Class<?>[] methodParameters;
	private Plugin plugin;
	
	EconomyPlugin(String methodClass, String methodName, Class<?>[] methodParameters, Plugin plugin)
	{
		this.methodClass = methodClass;
		this.methodName = methodName;
		this.methodParameters = methodParameters;
		this.plugin = plugin;
		
	}
	
	public String getMethodClass()
	{
		return methodClass;
	}
	
	
	public String getMethodName()
	{
		return methodName;
	}
	
	public Class<?>[] getMethodParameters()
	{
		return methodParameters;
	}
	
	public Plugin getPlugin()
	{
		return plugin;
	}
}
