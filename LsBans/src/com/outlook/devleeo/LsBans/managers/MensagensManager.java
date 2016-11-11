package com.outlook.devleeo.LsBans.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;


public class MensagensManager {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	public static final String PREFIX = getMensagem("Prefix"); 
	
	public static String getMensagem(String path) {
		return formatMessage(config.getString("Mensagens." + path));
	}
	
	public static String formatMessage(String msg) {
		return msg
				.replace("&", "§")
				.replace("voce", "você")
				.replace("Voce", "Você")
				.replace("nao", "não")
				.replace("Nao", "Não")
				.replace("ninguem", "ninguém")
				.replace("Ninguem", "Ninguém")
				.replace("numero", "número")
				.replace("Numero", "Número")
				.replace("ja", "já")
				.replace("sera", "será")
				.replace("preco", "preço")
				.replace("Preco", "Preço")
				.replace("invalida", "inválida")
				.replace("invalido", "inválido")
				.replace("Parabens", "Parabéns")
				.replace("parabens", "parabéns")
				.replace("proxima", "próxima")
				.replace("Proxima", "Próxima")	
				.replace("%>>%", "»")
				.replace("\\n", "\n");
	}

}
