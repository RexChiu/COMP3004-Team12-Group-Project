package game;

import java.util.HashMap;

public class Tokens {
	
	private HashMap <String, Token> tokens;
	
	public Tokens(String token) {
		// TODO Auto-generated constructor stub
		tokens = new HashMap<String, Token>();
		tokens.put(token, new Token(token));
	}
	
	public int		getSize()	{ return tokens.size(); }
}
