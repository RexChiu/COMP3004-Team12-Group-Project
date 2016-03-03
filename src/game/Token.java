package game;

public class Token {

	private String name;
	
	public Token(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
 
	public String 	getName() 	{ return this.name.substring(0, 1); }
	public String 	toString()	{ return this.name;	}
}
