package config;

import network.ServerThread;

public class Filter {

	private String[] filters =  { "Fred", "Joe", "Jill", "Jack"};
	
	public Boolean filter (ServerThread client, String input) {
		for (String filter : filters) {
			if (input.toLowerCase().contains(filter)) {				
				return Boolean.FALSE;
			}}
		return Boolean.TRUE;
	}
}