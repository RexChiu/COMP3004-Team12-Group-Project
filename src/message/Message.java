package message;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	//message types
	public static final String DATA 			= "Data";
	public static final String LOGIN 			= "Login";
	public static final String LOGOUT 			= "Logout";
	public static final String DISCONNECT 		= "Disconnect";
	
	//default header fields
	public static final String 	DEFAULT_SENDER 	= "Server";
	public static final String 	DEFAULT_RECEIVER= "Client";
    public static final String 	DEFAULT_TYPE 	= "Type";
    public static final int 	DEFAULT_STATE 	= -1;

	
	private static final long serialVersionUID = 6394396411894185136L;
	private Header header;
	private Body body;
	
	public Message() {
		header = new Header();
		body = new Body();
	}
	
	public Header 	getHeader() { return this.header; }
	public Body 	getBody()	{ return this.body; }
	
	public String toString(){
		return header.toString() + "\n" + body.toString();
	}
}
