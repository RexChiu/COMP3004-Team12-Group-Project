package network;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	private int ID = -1;
	private Socket 			socket 		= null;
	private AppServer 		server 		= null;
   private DataInputStream 	streamIn    = null;
   private DataOutputStream streamOut	= null;
	
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

	public void send(String msg) {
		try {
			streamOut.writeUTF(msg);
		} catch (IOException ioe) {
			server.remove(ID);
		}
	}

	public void run() {
		while (!done) {
			try {
				server.handle(ID, streamIn.readUTF());
			} catch (IOException ioe) {
				server.remove(ID);
				break;
			}
		}
	}

	public void open() throws IOException {
		streamIn 	= new DataInputStream(socket.getInputStream());
		streamOut 	= new DataOutputStream(socket.getOutputStream());
	}

	public void close() {
		try {
			if (socket 		!= null) socket.close();
			if (streamIn 	!= null) streamIn.close();
			
			this.done 		= true;
			this.socket 	= null;
			this.streamIn 	= null;
		} catch (IOException e) { }
	}
}