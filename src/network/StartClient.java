package network;

import config.LANConfig;

public class StartClient {
	public static void main(String[] argv) {
		new AppClient(LANConfig.DEFAULT_HOST, LANConfig.DEFAULT_PORT); 
	}
}
