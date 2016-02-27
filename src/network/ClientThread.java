package network;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {
  private Socket          socket   = null;
  private AppClient       client   = null;
  private DataInputStream streamIn = null;
  private boolean         done     = false;
  
  public ClientThread(AppClient client, Socket socket) {  
    this.client = client;
    this.socket = socket;
    this.open();  
    this.start();
  }
  
  public void open () {
    try {  
      streamIn  = new DataInputStream(socket.getInputStream());
      } catch(IOException ioe) {  
        System.out.println("Error getting input stream");
        client.stop();
      }
  }
  
  public void close () {
    done = true;
    try {  
      if (streamIn  != null) streamIn.close();
      if (socket    != null) socket.close();
      this.socket   = null;
      this.streamIn = null;
    } catch(IOException ioe) { 
     }  
  }
  
  public void run() {
    System.out.println("Client Thread " + socket.getLocalPort() + " running.");
    while (!done) { 
      try {  
        client.handle(streamIn.readUTF());
      } catch(IOException ioe) {  }
    }
  } 

}
