package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentServer implements Runnable {
	
	// size of buffer
	protected int backlog =10;
	// Port used for this service
	protected int port=10140;
	// Lifetime of the agentServer
	private boolean alive = true;
	
	private Logger logger;
	
	private String name;
	
	public AgentServer(int port, int backlog, Logger logger, String name) {
		this.port = port;
		this.backlog = backlog;
		this.logger = logger;
		this.name = name;
	}
	
	public AgentServer(int port, Logger logger, String name) {
		this.port = port;
		this.logger = logger;
		this.name = name;
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
				
				logger.log(Level.INFO,"Incoming Agent on "+name);
				// Recovery of data
				
				InputStream input = soc.getInputStream();
				

				// construire le classloader / associer le jar à la classe de l'agent
				BAMAgentClassLoader bam = new BAMAgentClassLoader(new URL[]{}, this.getClass().getClassLoader());
				

				// Construire le flux de reception de l'agent
				AgentInputStream ais = new AgentInputStream(soc.getInputStream(), bam);
				
				// Récupere le jar
				jarBinks = (Jar) ais.readObject();

				bam.addExtraClasses(jarBinks);
				
				// récuperer l'agent
				_Agent a = (_Agent) ais.readObject();
				// Initialiser l'agent
				a.init(bam, this, this.name);
				
				// Lancer l'agent sur un nouveau thread
				new Thread(a).start();
				
				// Fermer le flux
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
	
	public int getPort() {
		return port;
	}
	
	public void printOnLogger(String s) {
		logger.log(Level.FINE, s);
	}

}
