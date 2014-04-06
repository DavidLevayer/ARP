package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentServer implements Runnable {
	
	// size of buffer
	protected int backlog =10;
	// Port used for this service
	protected int port=10140;
	// Lifetime of the agentServer
	private boolean alive = true;
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
		
		Socket soc=null;
		Jar jarBinks = null;
		
		logger.log(Level.INFO,"Starting AgentServer named "+name);
		
		try {
			ServerSocket serverTCPSoc = new ServerSocket(port, backlog);
			
			while(alive) {
				// Waiting for an agent to arrive
				soc = serverTCPSoc.accept();
				
				logger.log(Level.INFO,"\nIncoming Agent on "+name);
				// Recovery of data
				
				//InputStream input = soc.getInputStream();				

				// Build the classLoader
				BAMAgentClassLoader bam = new BAMAgentClassLoader(new URL[]{}, this.getClass().getClassLoader());				

				// Build the stream for getting the Jar file
				AgentInputStream ais = new AgentInputStream(soc.getInputStream(), bam);
				
				// Get the JAr file
				jarBinks = (Jar) ais.readObject();

				bam.addExtraClasses(jarBinks);
				
				// Get the agent
				_Agent a = (_Agent) ais.readObject();
				// Initialize it
				a.init(bam, this, this.name);
				
				logger.log(Level.INFO,"Receiving complete. Launching Agent!");
				// Launch it on a new Thread
				new Thread(a).start();
				
				// Close the stream
				ais.close();
				
			}
			
			serverTCPSoc.close();
			
		} catch (IOException e) {
			System.out.println("AgentServer: error during the creation of the ServerSocket");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("AgentServer: 404 class not found");
			e.printStackTrace();
		}
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

}
