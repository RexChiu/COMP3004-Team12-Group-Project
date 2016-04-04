package network;

import java.net.*;
import java.io.*;

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
		if (message.getHeader().state == LANConfig.PLAYER_LOSS){
			stop();
		}
		UI.updateUI(message);
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
		UI.shutDown();
		System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_SHUTDOWN);
	}
}