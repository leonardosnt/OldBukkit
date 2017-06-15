package com.outlook.devleeo.LsMagnata.common;


public class SysLogger
{

	public static void log(String msg)
	{
		System.out.println("[LsMagnata] " + msg);
	}
	
	public static void error(String msg)
	{
		System.err.println("[LsMagnata] " + msg);
	}
	
}
