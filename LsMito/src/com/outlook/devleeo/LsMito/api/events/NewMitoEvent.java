package com.outlook.devleeo.LsMito.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class NewMitoEvent extends PlayerEvent
{
	private static final HandlerList HANDLERS = new HandlerList();
	private String oldmito;
	
	public NewMitoEvent(Player who, String oldMito)
	{
		super(who);
		this.oldmito = oldMito;
	}

	public String getOldmito()
	{
		return oldmito;
	}
	
	public HandlerList getHandlers()
	{
		return HANDLERS;
	}

	public static HandlerList getHandlerList()
	{
		return HANDLERS;
	}

}
