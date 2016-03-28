
package app;

import config.LANConfig;
import gui.HostPanel;
import network.AppServer;

public class ServerIvanhoe {
	
	private static Boolean done = Boolean.FALSE;
	private static Boolean started = Boolean.FALSE;

	private static AppServer appServer = null;
	
	private static HostPanel host = null;
	
	public static void main(String args[]) { 
		host = new HostPanel();
		host.setVisible(Boolean.TRUE);

		do {
			System.out.print("");
			if (host.isStart() && !started){
				host.writeMessage(LANConfig.SERVER_STATUS_START);
				appServer = new AppServer(LANConfig.DEFAULT_PORT, host);
				started = Boolean.TRUE;
			}
			
			if (!host.isStart() && started){
				appServer.shutdown();
				started = Boolean.FALSE;
				host.writeMessage(LANConfig.SERVER_STATUS_SHUTDOWN);
			}
			if (!host.isVisible()){
				done = Boolean.TRUE;
			}
		} while (!done);
		
		System.exit(0);
	}
}
