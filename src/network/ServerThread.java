package network;

import java.net.*;
import java.io.*;
import message.Message;

public class ServerThread extends Thread {
	private int ID = -1;
	private Socket 				socket 				= null;
	private AppServer 			server 				= null;
	private ObjectInputStream 	objectInputStream   = null;
   	private ObjectOutputStream	objectOutputStream	= null;
	
	private String clientAddress = null;;

	private boolean done = false;

	public ServerThread(AppServer server, Socket socket) {
		super();
		this.server 		= server;
		this.socket 		= socket;
		this.ID 			= socket.getPort();
		this.clientAddress 	= socket.getInetAddress().getHostAddress();
	}

	public int getID() { return this.ID; }

	public String getSocketAddress () { return clientAddress; }

	public void send(Message message) {
		try { objectOutputStream.writeObject(message); } 
		catch (IOException ioe) { server.remove(ID); }
	}

	public void run() {
		while (!done) {
			try {
		        Message message = (Message)objectInputStream.readObject();
		        server.handle(ID, message);
			} catch (Exception ioe) {
				if(server.getClientNumber() > 0)
					server.remove(ID);
				break;
			}
		}
	}

	public void open() throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream =  new ObjectInputStream( socket.getInputStream());
	}

	public void close() {
		try {
			if (socket 				!= null) socket.close();
		    if (objectInputStream 	!= null) objectInputStream.close();
			
			this.done 				= true;
			this.socket 			= null;
			this.objectInputStream 	= null;
		} catch (IOException e) { }
	}
}