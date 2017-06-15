package com.outlook.devleeo.LsMito;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.outlook.devleeo.LsMito.commands.MitoCommand;
import com.outlook.devleeo.LsMito.commands.SetmitoCommand;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;
import com.outlook.devleeo.LsMito.configuration.DBFile;
import com.outlook.devleeo.LsMito.hooks.VaultPermissionsHook;
import com.outlook.devleeo.LsMito.listeners.LegendChatListener;
import com.outlook.devleeo.LsMito.listeners.PlayerListener;
import com.outlook.devleeo.LsMito.listeners.VanillaChatListener;
import com.outlook.devleeo.LsMito.threads.runnables.UpdateMitoRunnable;

public class LsMitoPlugin extends JavaPlugin
{

	private static LsMitoPlugin plugin;
	private static Scoreboard scoreboard;
	
	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		System.out.println("[LsMito] Habilitando plugin (v" + getDescription().getVersion() + ")");
		
		plugin = this;
		saveDefaultConfig();
		DBFile.setup();
		
		if (classExists("net.milkbowl.vault.permission.Permission"))
		{
			VaultPermissionsHook.setup();
		}
		
		ConfigDefaults.loadDefaults();
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("mito").setExecutor(new MitoCommand());
		getCommand("setmito").setExecutor(new SetmitoCommand());
		
		if (classExists("br.com.devpaulo.legendchat.api.events.ChatMessageEvent"))
		{
			getServer().getPluginManager().registerEvents(new LegendChatListener(), this);
		}
		else
		{
			getServer().getPluginManager().registerEvents(new VanillaChatListener(), this);
		}
		
		if (ConfigDefaults.enablePrefixHead)
		{
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Team mito = scoreboard.getTeam("lsmito") == null ? scoreboard.registerNewTeam("lsmito") : scoreboard.getTeam("lsmito");
			mito.setPrefix(ConfigDefaults.prefixHead);
		}
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new UpdateMitoRunnable(), 0, ConfigDefaults.updateDelay * 20);
		
		System.out.println("[LsMito] Plugin habilitado (" +(System.currentTimeMillis() - start)+ "ms)");
	}
	
	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();
		System.out.println("[LsMito] Desabilitando plugin (v" + getDescription().getVersion() + ")");
		HandlerList.unregisterAll(this);
		System.out.println("[LsMito] Plugin desabilitado (" +(System.currentTimeMillis() - start)+ "ms)");
		System.gc();
	}
	
	public static LsMitoPlugin getPlugin()
	{
		return plugin;
	}
	
	public static Scoreboard getScoreboard()
	{
		return scoreboard;
	}
	
	private boolean classExists(String clazz)
	{
		try
		{
			Class.forName(clazz);
			return true;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
	}

}
