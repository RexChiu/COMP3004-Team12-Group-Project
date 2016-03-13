package network;

import java.net.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.GAMEConfig;
import config.LANConfig;
import gui.ClientPanel;
import message.Message;

public class AppClient implements Runnable {

	private int ID = 0;
	private Socket socket = null;
	private Thread thread = null;
	private ClientThread client = null;
	private ObjectOutputStream objectOutputStream = null;
	private ClientPanel UI;

	public AppClient(String serverName, int serverPort, ClientPanel UI) throws IOException {
		try {
			this.socket = new Socket(serverName, serverPort);
			System.out.println(ID + ": Establishing connection. Please wait ...");
			this.ID = socket.getLocalPort();
			this.UI = UI;
			System.out.println(ID + ": Connected to server: " + socket.getInetAddress());
			System.out.println(ID + ": Connected to portid: " + socket.getLocalPort());
			this.start();
		} catch (IOException e) {
			throw new IOException();
		}
	}

	public int getID() { return this.ID; }

	public void start() throws IOException {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			if (thread == null) {
				client = new ClientThread(this, socket);
				thread = new Thread(this);
				thread.start();
			}
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	public void run() {
		System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_START);
		while (thread != null) {}
		System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_SHUTDOWN);
	}

	public void send(Message message) {
		try {
			if (objectOutputStream != null) {
				objectOutputStream.writeObject(message);
				objectOutputStream.flush();
			} else {
				System.out.println(ID + ": " + LANConfig.CLIENT_STREAM_CLOSE);
			}
		} catch (IOException e) {
			stop();
		}
	}

	public void handle(Message message) {
		String type = message.getHeader().getType();
		System.out.println("Client State: "  + type);
		int state = message.getHeader().getState();
		
		if (state == GAMEConfig.GAME_SETUP){
			String firstPlayer = message.getBody().getField("First Player").toString();
			JOptionPane.showMessageDialog(new JFrame(), LANConfig.GAME_READY + " First player: " + firstPlayer, LANConfig.SERVER,
					JOptionPane.INFORMATION_MESSAGE);				
			UI.updateUI(message);						
		} else if (state == GAMEConfig.START_TOURNAMENT){
		} else if (state == GAMEConfig.DEAL_CARD){
			if (message.getBody().hasField("Next Player")){
				send(message);
			}
			UI.updateUI(message);		
		}else if (state == GAMEConfig.SELECT_COLOUR){			
			UI.updateUI(message);
			String selectedColour = "";
			if (message.getBody().hasField("Selected Colour"))
				selectedColour = message.getBody().getField("Selected Colour").toString();
			Message response = new Message();
			response.getHeader().setType(GAMEConfig.TYPE_CONFIRM_COLOUR);
			response.getHeader().setState(GAMEConfig.CONFIRM_COLOUR);
			response.getBody().addField("Colour", selectedColour);
			send(response);		
		}else if (state == GAMEConfig.CONFIRM_COLOUR){
			System.out.println("CONFIRM_COLOUR");
			UI.updateUI(message);
		}else if (state == GAMEConfig.PLAY_OR_WITHDRAW){
			UI.updateUI(message);
			if (message.getBody().hasField("Player Withdrawn")){
				Message response = new Message();
				response.getHeader().setType(GAMEConfig.TYPE_CONFIRM_TOURNAMENT);
				response.getHeader().setState(GAMEConfig.CONFIRM_TOURNAMENT);	
				send(response);	
			}else{
				Message response = new Message();
				response.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
				response.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
				response.getBody().addField("POW Choice", message.getBody().getField("POW Choice"));
				send(response);	
			}
		}else if (state == GAMEConfig.PLAY_CARD){			
			UI.updateUI(message);
		}else if (state == GAMEConfig.CONFIRM_TOURNAMENT){
			UI.updateUI(message);			
			Message response = new Message();
			response.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
			response.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
			response.getBody().addField("POW Choice", message.getBody().getField("POW Choice"));
			send(response);				
		}else if (state == GAMEConfig.CONFIRM_REQUEST){
			//UI.updateUI(message);
		}else if (state == GAMEConfig.WIN_TOURNAMENT){
			send(message);		
		}else if (state == GAMEConfig.GAME_OVER){
			UI.updateUI(message);
		}
	}

	public void stop() {
		try {
			if (thread != null)
				thread = null;
			if (objectOutputStream != null)
				objectOutputStream.close();
			if (socket != null)
				socket.close();
			this.socket = null;
			this.objectOutputStream = null;
		} catch (IOException ioe) {
		}
		client.close();
		System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_SHUTDOWN);
	}
}