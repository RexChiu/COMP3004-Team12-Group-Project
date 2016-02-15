package network;

import java.io.*;
import java.util.*;

import config.LANConfig;

public class StartServer {
	
	private static Boolean done = Boolean.FALSE;
	private static Boolean started = Boolean.FALSE;

	private static Scanner keyboard = new Scanner(System.in);
	private static AppServer appServer = null;
	
	public StartServer(){
		Console c = System.console();
		if (c == null) {
			System.err.println("No System Console....");
			System.err.println("Use IDE Console....");
		}
		
		do {
			String input = keyboard.nextLine();
			
			if (input.equalsIgnoreCase("START") && !started)
			{
				System.out.println("Starting server ...");
				appServer = new AppServer(LANConfig.DEFAULT_PORT);
				started = Boolean.TRUE;
			}
			
			if (input.equalsIgnoreCase("SHUTDOWN") && started)
			{
				System.out.println("Shutting server down ...");
				appServer.shutdown();
				started = Boolean.FALSE;
				done = Boolean.TRUE;
			}			
		} while (!done);

		System.exit(1);
	}
	
	
	public static void main(String[] argv) {
	
		Console c = System.console();
		if (c == null) {
			System.err.println("No System Console....");
			System.err.println("Use IDE Console....");
		}
		
		do {
			String input = keyboard.nextLine();
			
			if (input.equalsIgnoreCase("START") && !started)
			{
				System.out.println("Starting server ...");
				appServer = new AppServer(LANConfig.DEFAULT_PORT);
				started = Boolean.TRUE;
			}
			
			if (input.equalsIgnoreCase("SHUTDOWN") && started)
			{
				System.out.println("Shutting server down ...");
				appServer.shutdown();
				started = Boolean.FALSE;
				done = Boolean.TRUE;
			}			
		} while (!done);

		System.exit(1);
	}
}

