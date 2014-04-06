package jus.aor.mobilagent.kernel;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main server, which allows the launch of a server of mobile agents. It also provides functions 
 * which make it possible to deploy both services and agents
 * @author david
 */
public final class Server {
	// Logic name of the server
	protected String name;
	// Port where the service will be set. By default, use 10140
	protected int port=10140;
	// Starting node of the server of agents
	protected AgentServer agentServer;
	// Name of the logger
	protected String loggerName;
	// Logger of this server
	protected Logger logger=null;
	// Site
	private String site;

	private AgentServer as;

	/**
	 * Start a mobile agent server
	 * @param port Listening port for the mobile agent server
	 * @param name Name of the server
	 */
	public Server(final int port, final String name, Logger loggerParent){

		try {
			// Set the logger in order to print a trace of the process
			//loggerName = "jus/aor/mobilagent/"+InetAddress.getLocalHost().getHostName()+"/"+name;
			//logger=Logger.getLogger(loggerName);

			//logger.setUseParentHandlers(true);
			//logger.setLevel(Level.parse(System.getProperty("LEVEL")));
			this.logger = loggerParent;
			logger.log(Level.INFO, "Starting of the server; name:"+name+" port:"+port);

			site = "tpmobileagent://localhost:"+port+"/";
			// Set the server parameters
			this.name = name;
			this.port=port;

			// Create and start a new agentServer
			as = new AgentServer(port,logger,this.name);
			new Thread(as).start();

			// Delay in the installation of the mobile agent server
			Thread.sleep(1000);

		}catch(Exception ex){
			logger.log(Level.INFO," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}

	/**
	 * Add a service the the server
	 * @param name Name of the service
	 * @param classeName Class of the service
	 * @param codeBase Codebase of the service
	 * @param args Arguments used to build the service
	 */
	public final void addService(String name, String classeName, String codeBase, Object... args) {
		try {
			
			logger.log(Level.INFO,"Deploying service based on "+codeBase+"...");
			
			BAMServerClassLoader loader = new BAMServerClassLoader(new URL[]{},this.getClass().getClassLoader());
			loader.addURL(codeBase);
			
			@SuppressWarnings("unchecked")
			Class<_Service<?>> serviceClasse = (Class<_Service<?>>)Class.forName(classeName,true,loader);
			
			_Service<?> s = (_Service<?>) serviceClasse.getConstructor(Object[].class).newInstance(new Object[]{args});
			
			as.addService(name,s);
			
			logger.log(Level.INFO,"                 ...Service successfully deployed!\n\n");

		}catch(Exception ex){
			logger.log(Level.INFO," erreur durant le lancement du service"+this,ex);
			return;
		}
	}
	/**
	 * Deploy an agent
	 * @param classeName class of the agent
	 * @param args parameters of the agent
	 * @param codeBase byte code of the agent class
	 * @param etapeAddress step list of the agent's road map
	 * @param etapeAction action list of the agent's road map
	 */
	public final void deployAgent(String classeName, Object[] args, String codeBase, List<String> etapeAddress, List<String> etapeAction) {
		try {
			logger.log(Level.INFO,"Deploying Agent based on "+codeBase+"...");
			_Agent a;
			
			BAMAgentClassLoader loader = new BAMAgentClassLoader(new URL[]{},this.getClass().getClassLoader());
			loader.addURL(codeBase);
			
			@SuppressWarnings("unchecked")
			Class<_Agent> agentClasse = (Class<_Agent>)Class.forName(classeName,true,loader);

			a = (_Agent) agentClasse.getConstructor(Object[].class).newInstance(new Object[]{args});
			a.init(loader,this.as, this.name);

			a.addEtape(new Etape(new URI(this.site),_Action.NIHIL));
			// Creation of the road map
			for(int i = 0; i < etapeAction.size();i++) {
				Field f = a.getClass().getDeclaredField(etapeAction.get(i));
				f.setAccessible(true);
				_Action act = (_Action) f.get(a);
				Etape etap = new Etape(new URI(etapeAddress.get(i)), act);
				a.addEtape(etap);
			}
			
			Field f = a.getClass().getDeclaredField("retour");
			f.setAccessible(true);
			_Action act = (_Action) f.get(a);
			a.addEtape(new Etape(new URI(this.site), act));
			
			logger.log(Level.INFO,"           ...Agent successfully deployed");
			new Thread(a).start();

		}catch(Exception ex){
			logger.log(Level.INFO," erreur durant le deploiement de l'agent"+this,ex);
		}
	}
}
