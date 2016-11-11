package com.outlook.devleeo.LsPin.pinManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.outlook.devleeo.LsPin.filesmanager.Config;

public class Pin {
	
	private static Map<String, String> pins = new HashMap<>();
	private static final char[] LETRAS = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};	
	private static final char[] NUMEROS = {'0','1','2','3','4','5','6','7','8','9'};
	
	private static String gerar() {
		Random r = new Random();
		int tamanho = Config.TAMANHO;
		boolean usarLetras = Config.USAR_NUMEROS_E_LETRAS;
		boolean maiusAndMinus = Config.MAISCULAS_E_MINUSCULAS;
		String gerado = "";
		
		for (int i = 0; i < tamanho; i++) {
			int ran = r.nextInt(3);
			if (!usarLetras) {
				ran = 1;
			}
			if (!maiusAndMinus) {
				ran = r.nextInt(2);
			}
			switch (ran) {
				case 0:
					gerado += String.valueOf(LETRAS[r.nextInt(LETRAS.length)]).toLowerCase();
					break;
				case 1:
					gerado += String.valueOf(NUMEROS[r.nextInt(NUMEROS.length)]);
					break;
				case 2:
					gerado += String.valueOf(LETRAS[r.nextInt(LETRAS.length)]).toUpperCase();
					break;
				default:
					break;
			}
		}
		return gerado;
	}
	
	public static Map<String, String> getPins() {
		return pins;
	}
	
	public static void setPins(Map<String, String> pins) {
		Pin.pins = pins;
	}
	
	public static void removePin(String nome) {
		pins.remove(nome.toLowerCase());
	}
	
	public static void setPin(String nome, String pin) {
		pins.put(nome.toLowerCase(), pin);
	}
	
	public static String getPin(String nome) {
		return pins.get(nome.toLowerCase());
	}
	
	public static boolean hasPin(String nome) {
		return pins.containsKey(nome.toLowerCase());
	}
	
	public static void ativar(String nome) {
		setPin(nome, gerar());
	}
	
	public static String gerarSenha() {
		String senha = "";
		for (int i = 0; i < 6; i++) {
			senha += String.valueOf(NUMEROS[new Random().nextInt(NUMEROS.length)]);
		}
		return senha;
	}
}
