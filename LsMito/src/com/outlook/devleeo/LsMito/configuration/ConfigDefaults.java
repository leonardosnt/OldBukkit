package com.outlook.devleeo.LsMito.configuration;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.outlook.devleeo.LsMito.LsMitoPlugin;

public class ConfigDefaults
{

	public static String prefixChat;
	public static boolean enablePrefixChat;
	public static String prefixHead;
	public static boolean enablePrefixHead;
	public static boolean spawnLightningEffect;
	public static boolean spawnBats;
	public static boolean enableBatName;
	public static boolean debugMessages;
	public static String batName;
	public static int batsDespawnDelay;
	public static int batsAmout;
	public static int updateDelay;
	public static boolean enablePermissions;
	public static boolean enableRunCommands;
	
	public static String newMitoMessage;
	public static String currentMitoMessage; 
	public static String mitoJoinedMessage;
	public static String mitoExitedMessage;
	public static String noneMitoMesage;
	public static String notEnoughPermissions;
	
	public static List<String> runCommands;
	public static List<String> mitoPermissions;
	
	public static void loadDefaults()
	{
		FileConfiguration config = LsMitoPlugin.getPlugin().getConfig();
		
		prefixChat = getConfigString("Settings.Prefix.Chat.Prefix");
		prefixHead = getConfigString("Settings.Prefix.Head.Prefix");
		enablePrefixChat = config.getBoolean("Settings.Prefix.Chat.Enable");
		enablePrefixHead = config.getBoolean("Settings.Prefix.Head.Enable");;
		
		spawnLightningEffect = config.getBoolean("Settings.Spawn.LightningEffect.Enable");
		spawnBats = config.getBoolean("Settings.Spawn.Bats.Enable");
		enableBatName = config.getBoolean("Settings.Spawn.Bats.Name.Enable");
		batName = getConfigString("Settings.Spawn.Bats.Name.Name");
		batsAmout = config.getInt("Settings.Spawn.Bats.Amount");
		batsDespawnDelay = config.getInt("Settings.Spawn.Bats.DespawnDelay");
		updateDelay = config.getInt("Settings.UpdateDelay");
		debugMessages = config.getBoolean("Settings.DebugMessages");
		
		newMitoMessage = getConfigString("Settings.Messages.New");
		currentMitoMessage = getConfigString("Settings.Messages.Current");
		mitoJoinedMessage = getConfigString("Settings.Messages.Joined");
		mitoExitedMessage = getConfigString("Settings.Messages.Exited");
		noneMitoMesage = getConfigString("Settings.Messages.None");
		notEnoughPermissions = getConfigString("Settings.Messages.NotEnoughPermissions");
		
		runCommands = config.getStringList("RunCommands.Commands");
		mitoPermissions = config.getStringList("Permissions.Permissions");
		enableRunCommands = config.getBoolean("RunCommands.Enable");
		enablePermissions = config.getBoolean("Permissions.Enable");
		
	}
	
	private static String getConfigString(String path)
	{
		return LsMitoPlugin.getPlugin().getConfig().getString(path, "§4[Erro] Path (" + path + ") nao encontrado.").replace("\\n", "\n").replaceAll("(?i)&([a-z0-9])", "§$1");
	}
	
}
