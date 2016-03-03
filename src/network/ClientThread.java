package network;

import java.io.*;
import java.net.*;
import message.Message;

public class ClientThread extends Thread {
	private Socket socket = null;
	private AppClient client = null;
	private ObjectInputStream objectInputStream = null;
	private boolean done = false;

	public ClientThread(AppClient client, Socket socket) {
		this.client = client;
		this.socket = socket;
		this.open();
		this.start();
	}

	public void open() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("Error getting input stream");
			client.stop();
		}
	}

	public void close() {
		done = true;
		try {
			if (objectInputStream != null)
				objectInputStream.close();
			if (socket != null)
				socket.close();
			this.socket = null;
			this.objectInputStream = null;
		} catch (IOException ioe) {
		}
	}

	public void run() {
		System.out.println("Client Thread " + socket.getLocalPort() + " running.");
		while (!done) {
			try {
				Message message = (Message) objectInputStream.readObject();
				client.handle(message);
			} catch (Exception ioe) {
			}
		}
	}

}
