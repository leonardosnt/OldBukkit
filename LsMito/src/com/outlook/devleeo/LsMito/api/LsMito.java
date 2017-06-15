package com.outlook.devleeo.LsMito.api;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.outlook.devleeo.LsMito.LsMitoPlugin;
import com.outlook.devleeo.LsMito.api.events.NewMitoEvent;
import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;
import com.outlook.devleeo.LsMito.configuration.DBFile;
import com.outlook.devleeo.LsMito.threads.runnables.RemoveBatsRunnable;
import com.outlook.devleeo.LsMito.threads.runnables.UpdateMitoRunnable;

public class LsMito
{

	public static boolean isMito(Player player)
	{
		return isMito(player.getName());
	}
	
	public static boolean isMito(String name)
	{
		if (getMito() == null) return false;
		return getMito().equalsIgnoreCase(name);
	}
	
	public static String getMito()
	{
		return DBFile.getMito();
	}

	public static void setMito(String playerName)
	{
		
		Player newMito = Bukkit.getPlayer(playerName);
		String oldMito = getMito();
		
		if (newMito != null)
		{
			NewMitoEvent newMitoEvent = new NewMitoEvent(newMito, oldMito);
			
			Bukkit.getPluginManager().callEvent(newMitoEvent);
			
			if (ConfigDefaults.spawnBats)
			{
				HashSet<Bat> spawnedBats = new HashSet<>();
				
				for (int i = 0; i < ConfigDefaults.batsAmout; i++)
				{
					Bat bat = newMito.getWorld().spawn(newMito.getLocation().add(0, .5, 0), Bat.class);
					
					if (ConfigDefaults.enableBatName)
					{
						bat.setCustomNameVisible(true);
						bat.setCustomName(ConfigDefaults.batName.replaceAll("(?i)\\{player\\}", newMito == null ? playerName : newMito.getName()));
					}
					
					spawnedBats.add(bat);
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(LsMitoPlugin.getPlugin(), new RemoveBatsRunnable(spawnedBats), ConfigDefaults.batsDespawnDelay * 20);
			}

			if (ConfigDefaults.spawnLightningEffect)
			{
				Vector[] vectors = {new Vector(1.5, 0, 0), new Vector(-1.5, 0, 0), new Vector(0, 0, 1.5), new Vector(0, 0, -1.5)};
				
				for (Vector vec : vectors)
				{
					newMito.getWorld().strikeLightningEffect(newMito.getLocation().add(vec));
				}
			}
		}
		
		if (ConfigDefaults.enableRunCommands)
		{
			for (String command : ConfigDefaults.runCommands)
			{
				if (oldMito == null && command.matches(".*(?i)\\{oldmito\\}.*")) continue;
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("(?i)\\{oldmito\\}", oldMito).replaceAll("(?i)\\{newmito\\}", newMito == null ? playerName : newMito.getName()));
			}
		}
		
		Bukkit.getScheduler().runTask(LsMitoPlugin.getPlugin(), new UpdateMitoRunnable());
		Bukkit.broadcastMessage(ConfigDefaults.newMitoMessage.replaceAll("(?i)\\{player\\}", newMito == null ? playerName : newMito.getName()));
		DBFile.setMito(newMito == null ? playerName : newMito.getName());
	}

	public static LsMitoPlugin getLsMito()
	{
		return LsMitoPlugin.getPlugin();
	}
	
}
