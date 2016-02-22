package network;

import java.net.*;
import java.util.HashMap;
import java.util.Set;

import config.Filter;
import config.LANConfig;
import gui.HostPanel;

import java.io.*;

public class AppServer implements Runnable {
	int clientCount = 0;
	private Filter filter;
	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> clients;
	private HostPanel host;
	
	public AppServer(int port, HostPanel host) {
		try {
			this.host = host;
			filter = new Filter();

			host.writeMessage("Binding to port " + port + ", please wait  ...");
			clients = new HashMap<Integer, ServerThread>();
			server 	= new ServerSocket(port);
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
		}
	}
	
	public AppServer(int port) {
		try {
			filter = new Filter();
			
			System.out.println("Binding to port " + port + ", please wait  ...");
			clients = new HashMap<Integer, ServerThread>();
			server 	= new ServerSocket(port);
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		while (thread != null) {
			try {
				addThread(server.accept());
			} catch (IOException e) {				
			}
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < LANConfig.MAX_CLIENTS) {
			try {
				/** Create a separate server thread for each client */
				ServerThread serverThread = new ServerThread(this, socket);
				/** Open and start the thread */
				serverThread.open();
				serverThread.start();
				clients.put(serverThread.getID(), serverThread);
				host.writeMessage(String.format("%5d: %s", serverThread.getID(), "Join the game."));
				this.clientCount++;
			} catch (IOException e) {
			}
		} else {
		}
	}

	public synchronized void handle(int ID, String input) {
		if (input.equals("quit!")) 
		{
			if (clients.containsKey(ID)) {
				clients.get(ID).send("quit!");
				remove(ID);
			}
		}
		if (input.equals("shutdown!")) { shutdown(); }
		else 
		{
			//ServerThread from = clients.get(ID);
			host.writeMessage(String.format("%5d: %s", ID, input));
			for (ServerThread to : clients.values()) {
				if (to.getID() != ID) {
					to.send(String.format("%5d: %s", ID, input));
				}
			}
		}
	}

	public synchronized void remove(int ID) {
		if (clients.containsKey(ID)) {
			host.writeMessage(String.format("%5d: %s", ID, "Quit the game"));
			ServerThread toTerminate = clients.get(ID);
			clients.remove(ID);
			clientCount--;

			toTerminate.close();
			toTerminate = null;
		}
	}

	public void shutdown() {
		Set<Integer> keys = clients.keySet();

		if (thread != null) {
			thread = null;
		}

		try {
			for (Integer key : keys) {
				clients.get(key).close();
				clients.put(key, null);
			}
			clients.clear();
			server.close();
		} catch (IOException e) { }
	}
	
}