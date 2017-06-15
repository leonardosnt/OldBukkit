package com.outlook.devleeo.LsMagnata;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.outlook.devleeo.LsMagnata.commands.MagnataCommand;
import com.outlook.devleeo.LsMagnata.common.SysLogger;
import com.outlook.devleeo.LsMagnata.common.YAMLConfig;
import com.outlook.devleeo.LsMagnata.enums.EconomyPlugin;
import com.outlook.devleeo.LsMagnata.hooks.VaultPermissionsHook;
import com.outlook.devleeo.LsMagnata.listener.LegendChatListener;
import com.outlook.devleeo.LsMagnata.listener.PlayerJoinListener;
import com.outlook.devleeo.LsMagnata.listener.PlayerQuitListener;
import com.outlook.devleeo.LsMagnata.listener.VanillaChatListener;
import com.outlook.devleeo.LsMagnata.threads.runnables.UpdateMagnataRunnable;

public class LsMagnata extends JavaPlugin
{

	public static Scoreboard scoreboard;
	private static EconomyPlugin economyPlugin;
	private static Plugin plugin;
	private static YAMLConfig database;
	
	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		
		saveDefaultConfig();
		plugin = this;

		if (classExists("org.melonbrew.fe.Fe"))
		{
			economyPlugin = EconomyPlugin.FE_ECONOMY;
			SysLogger.log("Sistema de economia: FeEconomy");
		}
		else if (classExists("com.iCo6.iConomy"))
		{
			economyPlugin = EconomyPlugin.ICONOMY;
			SysLogger.log("Sistema de economia: iConomy 6");
		} 
		else if (classExists("com.greatmancode.craftconomy3.Common"))
		{
			economyPlugin = EconomyPlugin.CRAFTECONOMY3;
			SysLogger.log("Sistema de economia: CraftEconomy3");
		}
		else
		{
			SysLogger.error("Nenhum plugin de economia encontrado, desabilitando plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		database = new YAMLConfig("db", this);
		database.createFile();
		
		getCommand("magnata").setExecutor(new MagnataCommand());
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		
		if (classExists("br.com.devpaulo.legendchat.api.events.ChatMessageEvent"))
		{
			SysLogger.log("Sistema de chat: Legendchat");
			pm.registerEvents(new LegendChatListener(), this);
		}
		else
		{
			SysLogger.log("Sistema de chat: Vanilla");
			pm.registerEvents(new VanillaChatListener(), this);
		}
		
		if (getConfig().getBoolean("Settings.EnableScoreboard"))
		{
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Team admin = scoreboard.registerNewTeam("magnata");
			admin.setPrefix(formatString(getConfig().getString("Settings.HeadPrefix")));
		}
		
		VaultPermissionsHook.setup();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new UpdateMagnataRunnable(), 0, getConfig().getInt("Settings.CheckDelayInSeconds", 60) * 20);
	
		SysLogger.log("LsMagnata v" + getDescription().getVersion() + " habilitado com sucesso ("+(System.currentTimeMillis() - start)+"ms)");

	}

	@Override
	public void onDisable()
	{
		SysLogger.log("LsMagnata v" + getDescription().getVersion() + " habilitado com sucesso.");
		if (database != null)
		{
			database.saveFile();
		}
	}
	
	public static Plugin getPlugin()
	{
		return plugin;
	}
	
	public static EconomyPlugin getEconomyPlugin()
	{
		return economyPlugin;
	}
 
	public static Plugin getPluginWithNotExactName(String name)
	{
		PluginManager pm = Bukkit.getPluginManager();
		for (Plugin plug : pm.getPlugins())
		{
			if (plug.getName().matches("(?i)^" + name + ".*"))
			{
				return plug;
			}
		}
		
		return null;
	}
	
	public static boolean classExists(String className)
	{
		try
		{
			Class.forName(className);
			
			return true;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
	}
	
	public static YAMLConfig getDatabaseFile()
	{
		return database;
	}
	
	public static String formatString(String s)
	{
		return s.replace("\\n", "\n").replaceAll("(?i)<prefix>", LsMagnata.getPlugin().getConfig().getString("Settings.MessagesPrefix", "String ("+s+") nao encontrada.")).replaceAll("(?i)&([a-z0-9])", "§$1");
	}


}
