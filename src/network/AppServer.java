package network;

import java.net.*;
import java.util.HashMap;
import java.util.Set;

import config.LANConfig;
import game.Ivanhoe;
import gui.HostPanel;
import message.Message;

import java.io.*;

public class AppServer implements Runnable {
	int clientCount = 0;
	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> clients;
	private HostPanel UI;
	private Ivanhoe rEngine;

	public AppServer(int port, HostPanel UI) {
		try {
			this.UI = UI;
			UI.writeMessage("Serer Host IP:" + LANConfig.DEFAULT_HOST);
			UI.writeMessage("Binding to port " + port + ", please wait  ...");
			UI.writeMessage("Max Clients of Serevr: " + LANConfig.NUM_CLIENTS);
			UI.writeMessage("Wariting for Client ...");

			rEngine = new Ivanhoe(LANConfig.NUM_CLIENTS);
			clients = new HashMap<Integer, ServerThread>();
			server = new ServerSocket(port);
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
		if (clientCount < LANConfig.NUM_CLIENTS) {
			try {
				/** Create a separate server thread for each client */
				ServerThread serverThread = new ServerThread(this, socket);
				/** Open and start the thread */
				serverThread.open();
				serverThread.start();
				clients.put(serverThread.getID(), serverThread);
				rEngine.addPlayer(serverThread.getID());
				UI.writeMessage(String.format("%5d: %s", serverThread.getID(), LANConfig.REQUEST_JOIN));
				this.clientCount++;

				if (clientCount == LANConfig.NUM_CLIENTS) {
					UI.writeMessage(LANConfig.GAME_READY);
					int ID = Integer.parseInt(rEngine.processInt(LANConfig.GAME_READY));
					String msg = String.format("%s: %s", LANConfig.SERVER, LANConfig.GAME_READY);
					for (ServerThread to : clients.values()) {
												
						UI.writeMessage(rEngine.getPlayer(to.getID()).toString());
						Message newMSG = new Message();
						newMSG.getBody().addField("Request", msg);
						to.send(newMSG);						
						newMSG = new Message();
						newMSG.getBody().addField("Request", LANConfig.PAKECT + rEngine.getData());
						to.send(rEngine.getMessage(to.getID()));
					}
				}
			} catch (IOException e) {
			}
		} else {
			try {
				ServerThread serverThread = new ServerThread(this, socket);
				serverThread.open();
				serverThread.start();
				Message newMSG = new Message();
				newMSG.getBody().addField("Request", LANConfig.CONNECTION_FULL);
				serverThread.send(newMSG);
				serverThread.close();
			} catch (IOException e) {
			}
		}
	}

	public synchronized void handle(int ID, Message message) {
		String input = (String) message.getBody().getField("Request");
		if (input.equals(LANConfig.CLIENT_QUIT)) {
			if (clients.containsKey(ID)) {
				Message newMSG = new Message();
				newMSG.getBody().addField("Request", LANConfig.CLIENT_QUIT);
				clients.get(ID).send(newMSG);
				rEngine.removePlayer(ID);
				remove(ID);
			}
		}
		if (input.equals(LANConfig.SERVER_SHUTDOWN)) {
			shutdown();
		} else {
			UI.writeMessage(String.format("%5d: %s", ID, input));
			for (ServerThread to : clients.values()) {
				if (to.getID() != ID) {
					Message newMSG = new Message();
					newMSG.getBody().addField("Request", LANConfig.CONNECTION_FULL);
					to.send(newMSG);
				}
			}
		}
	}

	public synchronized void remove(int ID) {
		if (clients.containsKey(ID)) {
			UI.writeMessage(String.format("%5d: %s", ID, LANConfig.REQUEST_QUIT));
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
		clientCount = 0;
		try {
			for (Integer key : keys) {
				UI.writeMessage(String.format("%5d: %s", key, LANConfig.REQUEST_QUIT));
				Message newMSG = new Message();
				newMSG.getBody().addField("Request", LANConfig.CLIENT_QUIT);
				clients.get(key).send(newMSG);
				clients.get(key).close();
				clients.put(key, null);
			}
			clients.clear();
			server.close();
		} catch (IOException e) {
		}
	}

}