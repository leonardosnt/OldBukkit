package com.outlook.devleeo.LsMito.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.outlook.devleeo.LsMito.LsMitoPlugin;

public class DBFile
{

	private static File file;
	
	public static void setup()
	{
		createFile();
	}
	
	private static void createFile()
	{
		try
		{
			file = new File(LsMitoPlugin.getPlugin().getDataFolder(), "dbfile.ls");
			if (!file.exists())
			{
				file.createNewFile();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getMito()
	{
		createFile();
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file)))
		{
			return fileReader.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setMito(String string)
	{
		createFile();
		
		try	(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file)))
		{
			fileWriter.write(string);
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
