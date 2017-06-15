package com.outlook.devleeo.LsMagnata.threads.runnables;

import org.apache.commons.lang.StringUtils;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.common.SysLogger;
import com.outlook.devleeo.LsMagnata.hooks.VaultPermissionsHook;
import com.outlook.devleeo.LsMagnata.managers.MagnataManager;
import com.outlook.devleeo.LsMagnata.threads.UpdatePermissionsThread;
import com.outlook.devleeo.LsMagnata.threads.UpdateScoreboardThread;

public class UpdateMagnataRunnable implements Runnable
{

	@Override
	public void run()
	{
		long start = 0;
		boolean debug = LsMagnata.getPlugin().getConfig().getBoolean("Settings.DebugConsole");
		
		if (debug)
		{
			start = System.currentTimeMillis();
			SysLogger.log("Checando magnata...");
		}
			
		String newMagnata = MagnataManager.getMoneyTopOne();
		
		if (!StringUtils.isBlank(LsMagnata.getDatabaseFile().getYmlFile().getString("current")))
		{
			
			if (!StringUtils.isBlank(newMagnata) && !newMagnata.equalsIgnoreCase(MagnataManager.getCurrentMagnata()))
			{
				MagnataManager.setCurrentMagnata(newMagnata);
			}
		}
		else
		{
			if (!StringUtils.isBlank(newMagnata))
			{
				LsMagnata.getDatabaseFile().getYmlFile().set("current", newMagnata);
				
				if (LsMagnata.getPlugin().getConfig().getBoolean("Messages.New.Enable"))
				{
					MagnataManager.setCurrentMagnata(newMagnata);
				}
			}
		}

		if (LsMagnata.getPlugin().getConfig().getBoolean("Settings.EnableScoreboard"))
		{
    		new UpdateScoreboardThread(newMagnata).start();
		}
		
		if (VaultPermissionsHook.getPermissionSystem() != null)
		{
			new UpdatePermissionsThread().start();
		}
		
		if (debug)
		{
			SysLogger.log("Checagem finalizada ("+(System.currentTimeMillis() - start)+"ms)");
		}
	}

}
