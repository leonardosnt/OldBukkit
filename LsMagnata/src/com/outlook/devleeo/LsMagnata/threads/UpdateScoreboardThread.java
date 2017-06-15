package com.outlook.devleeo.LsMagnata.threads;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.common.SysLogger;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;

public class UpdateScoreboardThread extends Thread
{

	private final String MAGNATA;
	
	public UpdateScoreboardThread(String newMagnata)
	{
		MAGNATA = newMagnata;
	}

	@Override
	public void run()
	{
		
		long start = 0;
		boolean debug = LsMagnata.getPlugin().getConfig().getBoolean("Settings.DebugConsole");
		
		if (debug)
		{
			start = System.currentTimeMillis();
			SysLogger.log("Atualizando scoreboard...");
		}
		
		Team magnataTeam = LsMagnata.scoreboard.getTeam("magnata");
		OfflinePlayer offCurrentMagnata = Bukkit.getOfflinePlayer(MAGNATA);
		
		if (!MagnataManager.getCurrentMagnata().equalsIgnoreCase(MAGNATA) || !magnataTeam.hasPlayer(offCurrentMagnata))
		{
			
			for (OfflinePlayer op : magnataTeam.getPlayers())
			{
				magnataTeam.removePlayer(op);
			}
			
			if (offCurrentMagnata != null)
			{
				magnataTeam.addPlayer(offCurrentMagnata);
			}
		}
		
		for (Player online : Bukkit.getOnlinePlayers())
		{
			online.setScoreboard(LsMagnata.scoreboard);
		}
		
		if (debug)
		{
			SysLogger.log("Atualizacao finalizada ("+(System.currentTimeMillis() - start)+"ms)");
		}
	}
}
