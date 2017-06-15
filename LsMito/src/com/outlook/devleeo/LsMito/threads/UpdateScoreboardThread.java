package com.outlook.devleeo.LsMito.threads;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.outlook.devleeo.LsMito.LsMitoPlugin;
import com.outlook.devleeo.LsMito.api.LsMito;

public class UpdateScoreboardThread extends Thread
{

	@Override
	public void run()
	{
		long start = System.currentTimeMillis();
		System.out.println("[LsMito] Atualizando scoreboard...");
		
		Team mito = LsMitoPlugin.getScoreboard().getTeam("lsmito");

		for (OfflinePlayer offTeamPlayer : mito.getPlayers())
		{
			if (!LsMito.isMito(offTeamPlayer.getName()))
			{
				mito.removePlayer(offTeamPlayer);
			}
		}
		
		for (Player online : Bukkit.getOnlinePlayers())
		{
			if (LsMito.isMito(online))
			{
				OfflinePlayer offMito = Bukkit.getOfflinePlayer(online.getName());
				
				if (offMito != null)
				{
					mito.addPlayer(offMito);
				}
			}
			online.setScoreboard(LsMitoPlugin.getScoreboard());
		}
		
		System.out.println("[LsMito] Scoreboard atualizado (" + (System.currentTimeMillis() - start)+ "ms)");
	}
}
