package network;

import java.net.*;
import java.util.HashMap;
import java.util.Set;

import config.GAMEConfig;
import config.LANConfig;
import game.Data;
import game.Ivanhoe;
import game.Player;
import gui.HostPanel;
import message.Message;

import java.io.*;

public class AppServer implements Runnable {
	private int clientCount = 0;
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

			rEngine = new Ivanhoe();
			clients = new HashMap<Integer, ServerThread>();
			server = new ServerSocket(port);
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
		}
	}
	
	public int getClientNumber()	{ return this.clientCount;	}

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
				ServerThread serverThread = new ServerThread(this, socket);
				serverThread.open();
				serverThread.start();
				clients.put(serverThread.getID(), serverThread);
				rEngine.addPlayer(serverThread.getID());
				UI.writeMessage(String.format("%5d: %s", serverThread.getID(), LANConfig.REQUEST_JOIN));
				this.clientCount++;

				if (clientCount == LANConfig.NUM_CLIENTS) {
					UI.writeMessage(LANConfig.GAME_READY);					
					rEngine.setup();
					
					HashMap<Integer, Player> players = rEngine.getPlayers();
					for (ServerThread to : clients.values()) {
						int ID = to.getID();
						to.send(Data.setup(players, ID));
					}
					
					int currentID= rEngine.getCurrentID();
					clients.get(currentID).send(Data.selectColor(players, currentID, GAMEConfig.NUMBER_COLOR_FIVE));
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

	public synchronized void handle(int ID, Message message){
		if (message.getHeader().state == LANConfig.PLAYER_LOSS){
			shutdown(ID);
			return;
		}		
		
		UI.writeMessage(String.format("%5d: %25s %20s", ID, GAMEConfig.STATE[rEngine.getPrevState()], GAMEConfig.STATE[rEngine.getState()]));
			
		Message response = rEngine.processMessage(message);
		if (response != null){
			HashMap<Integer, Player> players = rEngine.getPlayers();
			for (ServerThread to : clients.values()) {
				int tempID = to.getID();
				to.send(Data.getMessage(players, tempID));
			}			

			if (response.getHeader().state == GAMEConfig.GAME_OVER){
				for (ServerThread to : clients.values()) {
					int tempID = to.getID();
					to.send(Data.gameOver(players, tempID));
				}	
			}else{
				int currentID = rEngine.getCurrentID();
				clients.get(currentID).send(response);
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
			shutdown(ID);
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
	
	public void shutdown(int ID) {
		Set<Integer> keys = clients.keySet();
		if (thread != null) {
			thread = null;
		}
		clientCount = 0;
		try {
			UI.writeMessage(String.format("%5d: %s", ID, LANConfig.CLIENT_LOSS));
			for (Integer key : keys) {
				Message newMSG = new Message();
				newMSG.getHeader().state = LANConfig.PLAYER_LOSS;
				clients.get(key).send(newMSG);
				clients.get(key).close();
				clients.put(key, null);
			}
			clients.clear();
			server.close();
			rEngine = null;
			UI.stopHost();
		} catch (IOException e) {
		}
	}

}