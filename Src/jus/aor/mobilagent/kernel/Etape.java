package jus.aor.mobilagent.kernel;

import java.io.Serializable;
import java.net.URI;

/**
 * Define a step of a road map of an agent
 * @author  Morat
 */
public class Etape implements Serializable{
	
	private static final long serialVersionUID = 4102055378099993883L;
	
	// Address of the server concerned by the step
	protected URI server;
	// Action to achieve on this step
	protected _Action action;
	
	/**
	 * Creation of a step using a server address and an action
	 * @param server
	 * @param action
	 */
	public Etape(URI server, _Action action){
		this.server = server;
		this.action = action;
	}	
	
	public _Action getAction() {
		return this.action;
	}
	
	public String toString() {return server+"("+action+")";}
}
