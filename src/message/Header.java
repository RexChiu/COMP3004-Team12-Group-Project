package message;

import java.io.Serializable;

import config.GAMEConfig;

public class Header implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7729816603167728273L;
	public String 	sender;
	public String 	receiver;
	public String 	type;
	public int		state;
	
	public Header() {
		this.sender		= Message.DEFAULT_SENDER;
		this.receiver 	= Message.DEFAULT_RECEIVER;
		this.type 		= Message.DEFAULT_TYPE;
		this.state		= Message.DEFAULT_STATE;
	}
	
	public void		setSender(String sender)	{ this.sender 	= sender;	}
	public void		setReceiver(String receiver){ this.receiver = receiver;	}
	public void		setType(String type)		{ this.type 	= type;		}
	public void		setState(int state)			{ this.state 	= state;	}
	public String	getSender()					{ return this.sender; 		}
	public String	getReceiver()				{ return this.receiver;		}
	public String	getType()					{ return this.type;			}
	public int		getState()					{ return this.state;		}
	
	public String toString(){
		return "HEADER: Sender: " + sender + " Receiver: " + receiver + " Type: " + type + " State: " + GAMEConfig.STATE[state];
	}
}

