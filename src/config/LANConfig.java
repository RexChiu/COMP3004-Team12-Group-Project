package config;

public class LANConfig {
	public static 		int 	NUM_CLIENTS	 			= 2;
	public static 		int 	DEFAULT_PORT 			= 5050;
	public static 		String 	DEFAULT_HOST 			= "127.0.0.1";

	public static final int 	MIN_CLIENTS				= 2;
	public static final	int 	MAX_CLIENTS	 			= 5;
	public static final int 	MIN_PORT 				= 3000;
	public static final	int 	MAX_PORT 	 			= 10000;
	public static final boolean PRINT_STACK_TRACE 		= Boolean.FALSE;
	public static final int 	PLAYER_LOSS				= -99;
	
	
	// Network Status
	public static final String SERVER 					= "Ivanhoe";
	public static final String SERVER_STATUS_START		= "Starting server ...";
	public static final String SERVER_STATUS_SHUTDOWN	= "Shutting server down ...";
	public static final String CLIENT_STATUS_START		= "Client Started...";
	public static final String CLIENT_STATUS_SHUTDOWN	= "Client Stopped...";
	public static final String CLIENT_STREAM_CLOSE		= "Stream Closed...";
	public static final String GAME_READY				= "All players are joined the game...";
	public static final String PAKECT					= "Packet";
	
	
	//Dialog Message	
	public static final String SERVER_ERROR 			= "Server Error";
	public static final String CLIENT_ERROR 			= "Client Error";
	public static final String START_SERVER				= "Start Ivanhoe";
	public static final String STOP_SERVER 				= "Stop Ivanhoe";
	public static final String START_SERVER_REQUEST 	= "Do you want to start the host of Ivanhoe? ";
	public static final String STOP_SERVER_REQUEST		= "Do you want to stop the host of Ivanhoe?";
	public static final String CONNNECT_SERVER			= "Connect Server";
	public static final String DISCONNECT_SERVER		= "Disconnect Server";
	public static final String JOIN_SEREVR				= "Join Ivanhoe";
	public static final String QUIT_SERVER				= "Quit Ivanhoe";
	public static final String SERVER_DOWN				= "Ivanhoe is Down";
	public static final String SERVER_RUNNING			= "Ivanhoe is running";
	public static final String SERVER_NOT_RUNNING		= "Ivanhoe is not running";
	public static final String SERVER_FULL_CLIENTS		= "Ivanhoe is out of number of players";
	public static final String CLIENT_NOT_JOINED		= "Player is not joined to Ivanhoe";
	public static final String CONNECTION_FULL			= "Connection Full";
	public static final String REQUEST_JOIN				= "Join the game";
	public static final String REQUEST_QUIT				= "Quit the game";
	public static final String SERVER_SHUTDOWN			= "Shutdown!";
	public static final String CLIENT_QUIT				= "Quit!";
	public static final String CLIENT_LOSS				= "Client Loss!";
}
