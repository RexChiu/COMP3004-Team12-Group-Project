package network;

import java.net.*;
import java.util.HashMap;
import java.util.Set;

import config.GAMEConfig;
import config.LANConfig;
import game.Data;
import game.Ivanhoe;
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
					
					// Game Setup
					Message message = new Message();
					message.getHeader().setState(GAMEConfig.GAME_SETUP);
					int state = rEngine.processInput(message);
					message.getHeader().setState(state);
					message.getHeader().setType(GAMEConfig.TYPE_SET_UP);
					int currentID = rEngine.getCurrentID();
					
					for (ServerThread to : clients.values()) {
						Message response = Data.getMessage(rEngine.getPlayers(), to.getID());
						response.getHeader().setType(GAMEConfig.TYPE_SET_UP);
						response.getHeader().setState(GAMEConfig.GAME_SETUP);
						response.getBody().addField("First Player", Integer.toString(currentID));
						to.send(response);
					}

					// Deal Card to First Player
					message = new Message();
					message.getHeader().setState(GAMEConfig.DEAL_CARD);
					state = rEngine.processInput(message);
					message.getHeader().setState(state);
					message.getHeader().setType(GAMEConfig.TYPE_DEAL_CARD);
					currentID = rEngine.getCurrentID();
					for (ServerThread to : clients.values()) {
						Message response = Data.getMessage(rEngine.getPlayers(), to.getID());
						response.getHeader().setType(GAMEConfig.TYPE_DEAL_CARD);
						response.getHeader().setState(GAMEConfig.DEAL_CARD);
						to.send(response);						 
					}
					
					// RE change State from Deal Card to Select Color
					message = new Message();
					message.getHeader().setState(GAMEConfig.SELECT_COLOUR);
					state = rEngine.processInput(message);
					message.getHeader().setState(state);
					message.getHeader().setType(GAMEConfig.TYPE_SELECT_COLOUR);
					currentID = rEngine.getCurrentID();

					// Send Select Color to Client
					Message response = Data.getMessage(rEngine.getPlayers(), currentID);
					response.getHeader().setType(GAMEConfig.TYPE_SELECT_COLOUR);
					response.getHeader().setState(GAMEConfig.SELECT_COLOUR);
					response.getBody().addField("Choose Colour", 5);
					clients.get(currentID).send(response);
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
		UI.writeMessage(String.format("%5d: %25s %20s", ID, GAMEConfig.STATE[rEngine.getPrevState()], GAMEConfig.STATE[rEngine.getState()]));
		rEngine.processInput(message);
		int playerLeft  = rEngine.getPlayers().size();
		for (Integer key : rEngine.getPlayers().keySet()){
			if (rEngine.getPlayer(key).isWithdrawn())
				playerLeft--;
		}
		System.out.println(String.format("After  Ivanhoe State[%5d-%5d]: %20s %10s %10d", 
				ID, rEngine.getCurrentID(), GAMEConfig.STATE[rEngine.getState()], 
				rEngine.getPlayer(rEngine.getCurrentID()).isWithdrawn(), playerLeft));
		
		int currentID = rEngine.getCurrentID();
		int curState = rEngine.getState();
		
		if (curState == GAMEConfig.CONFIRM_TOURNAMENT){
			System.out.println("CONFIRM_TOURNAMENT");

			// Update Deal Card
			for (ServerThread to : clients.values()) {
				Message response = Data.getMessage(rEngine.getPlayers(), to.getID());
				response.getHeader().setType(GAMEConfig.TYPE_DEAL_CARD);
				response.getHeader().setState(GAMEConfig.DEAL_CARD);
				response.getBody().addField("Tournament Colour", rEngine.getCurrColour());
				to.send(response);						 
			}		
			if (playerLeft == 1){
				Message response = Data.getMessage(rEngine.getPlayers(), currentID);
				response.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				response.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
				response.getBody().addField("Player Withdrawn", GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				clients.get(currentID).send(response);	
			} else if (rEngine.getPlayer(currentID).isWithdrawn()){
				// Asking Play or Withdraw
				Message response = Data.getMessage(rEngine.getPlayers(), currentID);
				response.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				response.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
				response.getBody().addField("Player Withdrawn", GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				clients.get(currentID).send(response);	
			}else{
				// Asking Play or Withdraw
				Message response = Data.getMessage(rEngine.getPlayers(), currentID);
				response.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				response.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
				response.getBody().addField("POW Confirm",  GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				clients.get(currentID).send(response);		
			}
		} else if (curState == GAMEConfig.PLAY_CARD){			
			System.out.println("CONFIRM_REQUEST && PLAY_CARD");
			
			if (rEngine.isLegal()){
				Message response = Data.getMessage(rEngine.getPlayers(), currentID);
				response.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
				response.getHeader().setState(GAMEConfig.PLAY_CARD);
				response.getBody().addField("Legal Apply", GAMEConfig.APPLY_LEGAL);
				clients.get(currentID).send(response);		
			}	
		} else if (curState == GAMEConfig.CONFIRM_REQUEST) {
			Message response = Data.getMessage(rEngine.getPlayers(), currentID);
			response.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
			response.getHeader().setState(GAMEConfig.PLAY_CARD);
			response.getBody().addField("Legal Apply", GAMEConfig.APPLY_ILLEGAL);
			clients.get(currentID).send(response);				
		} else if (curState == GAMEConfig.WIN_TOURNAMENT){
			System.out.println("WIN_TOURNAMENT");
			
			// Send Win Tournament 
			Message response = Data.getMessage(rEngine.getPlayers(), currentID);
			response.getHeader().setType(GAMEConfig.TYPE_WIN_TOURNAMENT);
			response.getHeader().setState(GAMEConfig.WIN_TOURNAMENT);
			clients.get(currentID).send(response);					
		} else if (curState == GAMEConfig.GAME_OVER){
			System.out.println("GAME_OVER");

			for (ServerThread to : clients.values()) {
				Message response = Data.getMessage(rEngine.getPlayers(), to.getID());
				response.getHeader().setType(GAMEConfig.TYPE_GAME_OVER);
				response.getHeader().setState(GAMEConfig.GAME_OVER);
				response.getBody().addField("Winner", rEngine.getCurrentID());
				to.send(response);	
			}
		}
		System.out.println("############################################################################################\n\n");
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