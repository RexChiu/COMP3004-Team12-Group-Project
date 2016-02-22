package game;

import java.util.HashMap;

import config.GAMEConfig;

public class Tokens {
	
	private HashMap <String, Token> tokens;
	
	public Tokens(String token) {
		// TODO Auto-generated constructor stub
		tokens = new HashMap<String, Token>();
		tokens.put(token, new Token(token));
	}

	public void 	addToken(String token)		{ this.tokens.put(token, new Token(token)); }
	public void 	removeToken(String token)	{ this.tokens.remove(token);				}
	public boolean 	checkToken(String token)	{ return tokens.containsKey(token); 		}
	public Token 	getToken(String token)		{ return this.tokens.get(token);			}
	
	public int		getSize()	{ return tokens.size(); }
	
	// Check the token whether there is one or not
	public boolean 	hasPurple() { return tokens.containsKey(GAMEConfig.COLOR_PURPLE); 		}
}
