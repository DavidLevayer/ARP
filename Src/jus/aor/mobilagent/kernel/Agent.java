package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarException;
import java.util.logging.Logger;

public class Agent implements _Agent {

	private static final long serialVersionUID = 1L;

	protected Route roadmap;
	protected transient AgentServer agentServer;
	private transient String serverName;
	private transient BAMAgentClassLoader loader;
	protected String className;

	public void run() {
		// This method is called when an agent arrives on a server
		System.out.println("HelloWorld, I'm on "+serverName);
		Etape e = roadmap.next();
		e.getAction().execute();
		// We have to execute the action linked to the step
		if(roadmap.hasNext) {		
			//Move to the next server ??
			move();
		}
	}

	public void init(AgentServer agentServer, String serverName) {
		this.agentServer = agentServer;
		this.serverName = serverName;
		if(roadmap==null) {
			try {
				roadmap = new Route(new Etape(new URI(serverName), _Action.NIHIL));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	public void init(BAMAgentClassLoader loader, AgentServer server,
			String serverName) {
		this.init(server,serverName);
		this.loader = loader;		
	}

	public void addEtape(Etape etape) {
		roadmap.add(etape);		
	}

	private void move() {
		
		Jar myJar = null;
		Socket soc = null;
		OutputStream out;
		ObjectOutputStream outputStream;
		
		try {
			
			// prendre le jar et l'envoyer au serveur prochain
			//myJar = new Jar("Src/"+className.replace('.', '/')+".java");
			myJar = loader.getSavedJar();
			
			Etape next = roadmap.get();
			
			System.out.println("Trying to move to "+next.server.getHost()+":"+next.server.getPort());
			soc = new Socket(next.server.getHost(),next.server.getPort());
			out = soc.getOutputStream();

			outputStream = new ObjectOutputStream(out);

			outputStream.writeObject(myJar);

			// envoyer l'agent
			outputStream.writeObject(this);

			// libérer les ressources
			outputStream.close();
			out.close();
			soc.close();

		} catch (JarException e) {
			this.agentServer.printOnLogger("Agent.java: can't create JAR");
			e.printStackTrace();
		} catch (IOException e) {
			this.agentServer.printOnLogger("Agent.java: can't create the socket or etablish connection");
			e.printStackTrace();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
