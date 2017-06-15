package com.outlook.devleeo.LsMito.threads.runnables;

import java.util.HashSet;

import org.bukkit.entity.Bat;

public class RemoveBatsRunnable implements Runnable
{
	private HashSet<Bat> spawnedBats;

	public RemoveBatsRunnable(HashSet<Bat> spawnedBats) 
	{
		this.spawnedBats = spawnedBats;
	}
	
	@Override
	public void run()
	{
		for (Bat bat : spawnedBats)
		{
			bat.remove();
		}
		
		spawnedBats.clear();
	}

}
