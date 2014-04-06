package jus.aor.mobilagent.kernelRMI;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class Agent implements _Agent {

	private static final long serialVersionUID = 1L;

	protected Route roadmap;
	protected transient AgentServer agentServer;
	protected transient String serverName;
	private transient BAMAgentClassLoader loader;
	protected String className;
	protected long date;

	public void run() {
		// This method is called when an agent arrives on a server
		while(roadmap.hasNext) {
			Etape e = roadmap.next();
			serverName = e.getURI();
			e.getAction().execute();
		}
	}

	public String getServerName() {
		return this.serverName;
	}

	public void init(AgentServer agentServer, String serverName) {
		this.agentServer = agentServer;
		this.serverName = serverName;
		this.date = new Date().getTime();
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
		this.date = new Date().getTime();
	}

	public void addEtape(Etape etape) {
		roadmap.add(etape);		
	}
	
	public BAMAgentClassLoader getLoader () { return loader; }

}
