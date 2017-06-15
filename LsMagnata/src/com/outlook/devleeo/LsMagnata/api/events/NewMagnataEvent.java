package com.outlook.devleeo.LsMagnata.api.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NewMagnataEvent extends Event implements Cancellable
{
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	private String playerName;
		
	public NewMagnataEvent(String player)
	{
		this.playerName = player;
	}

	public Player getPlayer()
	{
		return Bukkit.getPlayer(playerName);
	}
	
	public String getPlayerName()
	{
		return playerName;
	}

	@Override
	public boolean isCancelled()
	{
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel)
	{
		this.isCancelled = cancel;
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
