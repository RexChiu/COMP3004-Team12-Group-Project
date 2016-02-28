package network;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.GUIConfig;
import config.LANConfig;
import gui.ClientPanel;

public class AppClient implements Runnable{
   
   private int 				ID			= 0;
   private Socket           socket    	= null;
   private Thread           thread   	= null;
   private ClientThread     client   	= null;
   private Scanner          keyboard  	= null;
   private DataInputStream  streamIn  	= null;
   private DataOutputStream	streamOut 	= null;
   private ClientPanel		UI;
   
   public AppClient(String serverName, int serverPort, ClientPanel UI) throws IOException{
      
      try{
         this.socket = new Socket(serverName, serverPort);
         System.out.println(ID + ": Establishing connection. Please wait ...");
         this.ID    = socket.getLocalPort();
         this.UI 	= UI;
         System.out.println(ID + ": Connected to server: " + socket.getInetAddress());
         System.out.println(ID + ": Connected to portid: " + socket.getLocalPort());
         this.start();
      }catch(IOException e){ 
    	  throw new IOException();
      }
   }

   public int getID(){ return this.ID; }

   public void start() throws IOException{
      try {
            keyboard    = new Scanner(System.in);
            streamIn    = new DataInputStream(socket.getInputStream());
            streamOut   = new DataOutputStream(socket.getOutputStream());

         if (thread == null) {  
            client = new ClientThread(this, socket);
            thread = new Thread(this);                   
            thread.start();
         }
       } catch (IOException ioe) {
         throw ioe;
      }
   }

   public void run(){
      System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_START);
      while (thread != null) {  
         /*try {  
            if (streamOut != null) {
               streamOut.writeUTF(keyboard.nextLine());
            } else {
               System.out.println(ID + ": Stream Closed");
            }
         }
         catch(IOException e) {  
            stop();
         }*/
      }
      System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_SHUTDOWN);
   }
   
   public void send(String message){
       try {  
           if (streamOut != null) {
              streamOut.writeUTF(message);
           } else {
              System.out.println(ID + ": " + LANConfig.CLIENT_STREAM_CLOSE);
           }
        }
        catch(IOException e) {  
           stop();
        }
   }

   public void handle (String msg) {
      if (msg.equalsIgnoreCase(LANConfig.CLIENT_QUIT)) {  
    	  UI.setClientJoined(Boolean.FALSE);
    	  UI.listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
    	  JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_DOWN, LANConfig.SERVER_ERROR, JOptionPane.INFORMATION_MESSAGE);
    	  stop();
      } else if (msg.equalsIgnoreCase(LANConfig.CONNECTION_FULL)){
    	  UI.setClientJoined(Boolean.FALSE);
    	  UI.listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
    	  JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_FULL_CLIENTS, LANConfig.SERVER_ERROR, JOptionPane.INFORMATION_MESSAGE);    	  
      } else {
          System.out.println("MESSAGE: " + msg);
          // Handle the message from the server.  
    	  if (msg.equals(String.format("%s: %s",LANConfig.SERVER, LANConfig.GAME_READY))){
        	  JOptionPane.showMessageDialog(new JFrame(), LANConfig.GAME_READY, LANConfig.SERVER, JOptionPane.INFORMATION_MESSAGE);    	
    	  }else if (msg.substring(0, 6).equals(LANConfig.PAKECT)){
    		  System.out.println(msg.substring(6));
    		  String[] player = msg.substring(6).split("/");
    		  String token 		= player[0].split(":")[1];
    		  System.out.println(token);
    		  String hand 		= player[0].split(":")[2];
    		  System.out.println(hand);
    		  String display 	= player[0].split(":")[3];
    		  System.out.print(display);
    		  
    	  }else if (msg.equals(Integer.toString(this.ID))){
        	  JOptionPane.showMessageDialog(new JFrame(), "According to purple token, you are dealer. Please click ok to Shuffle Deck and start game",
        			  LANConfig.SERVER, JOptionPane.INFORMATION_MESSAGE);       		  
    	  }
      }
   }


   public void stop() {  
      try { 
         if (thread != null)     thread = null;
         if (keyboard != null)   keyboard.close();
         if (streamIn != null)   streamIn.close();
         if (streamOut != null)  streamOut.close();

         if (socket != null)     socket.close();

         this.socket = null;
         this.keyboard = null;
         this.streamIn = null;
         this.streamOut = null;       
      } catch(IOException ioe) {  }
      client.close();  
      System.out.println(ID + ": " + LANConfig.CLIENT_STATUS_SHUTDOWN);
   }
}