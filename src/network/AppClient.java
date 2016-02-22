package network;

import java.net.*;
import java.io.*;
import java.util.*;

public class AppClient implements Runnable{
   
   private int ID = 0;
   private Socket             socket    = null;
   private Thread             thread    = null;
   private ClientThread       client    = null;
   private Scanner            keyboard  = null;
   private DataInputStream    streamIn  = null;
   private DataOutputStream   streamOut = null;

   public AppClient(String serverName, int serverPort){
      System.out.println(ID + ": Establishing connection. Please wait ...");
      
      try{
         this.socket = new Socket(serverName, serverPort);
         this.ID     = socket.getLocalPort();
         System.out.println(ID + ": Connected to server: " + socket.getInetAddress());
         System.out.println(ID + ": Connected to portid: " + socket.getLocalPort());
         this.start();
      }catch(IOException e){e.printStackTrace();}
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
      System.out.println(ID + ": Client Started...");
      while (thread != null) {  
         try {  
            if (streamOut != null) {
               streamOut.writeUTF(keyboard.nextLine());
            } else {
               System.out.println(ID + ": Stream Closed");
            }
         }
         catch(IOException e) {  
            stop();
         }
      }
      System.out.println(ID + ": Client Stopped...");
   }

   public void handle (String msg) {
      if (msg.equalsIgnoreCase("quit!")) {  
         System.out.println(ID + ": Good bye. Press RETURN to exit ...");
         stop();
      } else {
         System.out.println(msg);
         // Handle the message from the server.         
         
      }
   }


   public void stop() {  
      try { 
         if (thread != null)     thread = null;
         if (keyboard != null)    keyboard.close();
         if (streamIn != null)   streamIn.close();
         if (streamOut != null)  streamOut.close();

         if (socket != null)     socket.close();

         this.socket = null;
         this.keyboard = null;
         this.streamIn = null;
         this.streamOut = null;       
      } catch(IOException ioe) {  }
      client.close();  
   }
}