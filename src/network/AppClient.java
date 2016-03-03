package network;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.GUIConfig;
import config.LANConfig;
import game.Player;
import gui.ClientPanel;
import message.Message;

public class AppClient implements Runnable {

	private int ID = 0;
	private Socket socket = null;
	private Thread thread = null;
	private ClientThread client = null;
	private ObjectOutputStream objectOutputStream = null;
	private ClientPanel UI;

	private ArrayList<Player> players = new ArrayList<Player>();

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

	public int getID() {
		return this.ID;
	}

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
		if (message.getHeader().getType().equals("Setup")){
			UI.updateUI(message);
		}else{				
			String msg = (String) message.getBody().getField("Request");
			System.out.println(msg);

			if (msg.equalsIgnoreCase(LANConfig.CLIENT_QUIT)) {
				UI.setClientJoined(Boolean.FALSE);
				UI.listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
				JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_DOWN, LANConfig.SERVER_ERROR,
						JOptionPane.INFORMATION_MESSAGE);
				stop();
			} else if (msg.equalsIgnoreCase(LANConfig.CONNECTION_FULL)) {
				UI.setClientJoined(Boolean.FALSE);
				UI.listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
				JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_FULL_CLIENTS, LANConfig.SERVER_ERROR,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("MESSAGE: " + msg);
				// Handle the message from the server.
				if (msg.equals(String.format("%s: %s", LANConfig.SERVER, LANConfig.GAME_READY))) {
					JOptionPane.showMessageDialog(new JFrame(), LANConfig.GAME_READY, LANConfig.SERVER,
							JOptionPane.INFORMATION_MESSAGE);
				} else if (msg.substring(0, 6).equals(LANConfig.PAKECT)) {
				}
			}
		}
	}

	public Player getPlayer(int ID) {
		for (Player player : players) {
			if (player.getID() == ID)
				return player;
		}

		return null;
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