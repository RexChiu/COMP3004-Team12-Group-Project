package game;

import java.util.HashMap;

import config.GAMEConfig;

public class Tokens {
	
	private HashMap <String, Token> tokens;
	
	public Tokens() {
		// TODO Auto-generated constructor stub
		this.tokens = new HashMap<String, Token>();
	}

	public void 	addToken(String token)		{ this.tokens.put(token, new Token(token)); }
	public void 	removeToken(String token)	{ this.tokens.remove(token);				}
	public boolean 	checkToken(String token)	{ return tokens.containsKey(token); 		}
	public Token 	getToken(String token)		{ return this.tokens.get(token);			}
	public void 	cleanToken()				{ this.tokens.clear();						}
	
	public int		getSize()	{ return tokens.size(); }

	// Check total token that reach to winning requirement
	public boolean 	hasFour() 	{ return tokens.size() == GAMEConfig.ANY_FOUR_TOKENS; 		}
	public boolean 	hasFive() 	{ return tokens.size() == GAMEConfig.ALL_FIVE_TOKENS; 		}
		
	public String toString() {
		String result = "";
		if (tokens.isEmpty()) return result;
		
		for (int i = 0; i < tokens.keySet().size(); i++){
			String key = tokens.keySet().toArray()[i].toString();
			result += tokens.get(key).getName() + ",";
		}
		
		return result.substring(0, result.length()-1);
	}
}
