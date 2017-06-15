package com.outlook.devleeo.LsMito.threads.runnables;

import com.outlook.devleeo.LsMito.configuration.ConfigDefaults;
import com.outlook.devleeo.LsMito.hooks.VaultPermissionsHook;
import com.outlook.devleeo.LsMito.threads.UpdatePermissionsThread;
import com.outlook.devleeo.LsMito.threads.UpdateScoreboardThread;

public class UpdateMitoRunnable implements Runnable
{

	@Override
	public void run()
	{
		if (ConfigDefaults.debugMessages)
		{
			System.out.println("[LsMito] Atualizando mito...");
		}
		
		if (ConfigDefaults.enablePrefixHead)
		{
			new UpdateScoreboardThread().start();
		}

		if ((UpdatePermissionsThread.lastCheck + 300) < (System.currentTimeMillis() / 1000) && ConfigDefaults.enablePermissions && VaultPermissionsHook.getPermissionSystem() != null)
		{
			new UpdatePermissionsThread().start();
		}
	}

}
