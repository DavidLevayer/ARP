package jus.aor.mobilagent.kernelRMI;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentServer implements Runnable {
	
	// size of buffer
	protected int backlog =10;
	// Port used for this service
	protected int port=10140;
	// Service list
	private Map<String,_Service<?>> serviceList;
	
	private Logger logger;
	
	private String name;
	
	public AgentServer(int port, int backlog, Logger logger, String name) {
		this.port = port;
		this.backlog = backlog;
		this.logger = logger;
		this.name = name;
		serviceList = new HashMap<String,_Service<?>>();
	}
	
	public AgentServer(int port, Logger logger, String name) {
		this.port = port;
		this.logger = logger;
		this.name = name;
		serviceList = new HashMap<String,_Service<?>>();
	}

	/*
	 * After initialization, this function takes care of mobile agent
	 * and executes them as they arrive on a server
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		// Nothing to do, as the local agent is already started and agents don't
		// move anymore, which means that no agent will ever income here
		
	}
	
	public void addService (String name,_Service<?> s) {
		this.serviceList.put(name,s);
	}
	
	public _Service<?> getService(String name) {
		return serviceList.get(name);
	}
	
	public int getPort() {
		return port;
	}
	
	public void printOnLogger(String s) {
		logger.log(Level.FINE, s);
	}
	
	public String getName() { return name; }

}
