package com.outlook.devleeo.LsMagnata.managers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.outlook.devleeo.LsMagnata.LsMagnata;
import com.outlook.devleeo.LsMagnata.api.events.NewMagnataEvent;
import com.outlook.devleeo.LsMagnata.enums.EconomyPlugin;

public class MagnataManager
{

	public static String getCurrentMagnata()
	{
		return LsMagnata.getDatabaseFile().getYmlFile().getString("current", "");
	}
	
	public static String getMoneyTopOne()
	{
		try
		{
			Method topAccMethod;
			List<?> accs;
			EconomyPlugin ecoPlugin = LsMagnata.getEconomyPlugin();
			
			switch (ecoPlugin)
			{
				
				case FE_ECONOMY:
					Object feApiInstance = Class.forName(ecoPlugin.getMethodClass()).getConstructor(Class.forName("org.melonbrew.fe.Fe")).newInstance(ecoPlugin.getPlugin());
					topAccMethod = feApiInstance.getClass().getDeclaredMethod(ecoPlugin.getMethodName(), ecoPlugin.getMethodParameters());
					accs = (List<?>)topAccMethod.invoke(feApiInstance);
					
					if (accs.size() < 1)
					{
						return "";
					}
					else
					{
						Field nameField = accs.get(0).getClass().getDeclaredField("name");
						nameField.setAccessible(true);
						return (String) nameField.get(accs.get(0));
					}

				case ICONOMY:
					topAccMethod = Class.forName(ecoPlugin.getMethodClass()).getDeclaredMethod(ecoPlugin.getMethodName(), ecoPlugin.getMethodParameters());
					topAccMethod.setAccessible(true);
					accs = (List<?>)topAccMethod.invoke(ecoPlugin.getPlugin(), 1);
					
					if (accs.size() < 1)
					{
						return "";
					}
					else
					{
						Field nameField = accs.get(0).getClass().getDeclaredField("name");
						nameField.setAccessible(true);
						return (String) nameField.get(accs.get(0));
					}
					
				case CRAFTECONOMY3:
					return "";
					
				default:
					break;
			}
			
		}
		catch (InstantiationException| ClassNotFoundException | InvocationTargetException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		return "";

	}

	public static void setCurrentMagnata(String player)
	{
		NewMagnataEvent magnataEvent = new NewMagnataEvent(player);
		
		Bukkit.getPluginManager().callEvent(magnataEvent);
		
		if (magnataEvent.isCancelled()) return;
		
		LsMagnata.getDatabaseFile().getYmlFile().set("current", player);
		
		Player onlineMagnata = Bukkit.getPlayer(player);
		
		if (LsMagnata.getPlugin().getConfig().getBoolean("Messages.New.Enable"))
		{
			Bukkit.broadcastMessage(LsMagnata.formatString(LsMagnata.getPlugin().getConfig().getString("Messages.New.Message")).replaceAll("(?i)<player>", onlineMagnata == null ? player : onlineMagnata.getName()));
		}
		
		LsMagnata.getDatabaseFile().saveFile();

		if (onlineMagnata != null && LsMagnata.getPlugin().getConfig().getBoolean("Settings.PlayLightningEffect"))
		{
			Vector[] locs = {new Vector(.5, 0, 0), new Vector(-.5, 0, 0), new Vector(0, 0, .5), new Vector(0, 0, -.5)};
		
			for (Vector v : locs)
			{
				onlineMagnata.getWorld().strikeLightningEffect(onlineMagnata.getLocation().add(v));
			}
		}
	}
}
