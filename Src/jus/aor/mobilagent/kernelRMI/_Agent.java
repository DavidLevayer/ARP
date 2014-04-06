package jus.aor.mobilagent.kernelRMI;

import java.net.UnknownHostException;

/**
 * Description d'un agent du modele de bus a agents mobiles "BAM". Le constructeur d'un agent devra avoir la signature suivante : <bold>public XXXX(Object...)</bold>
 * @author  P.Morat
 */
public interface _Agent extends java.io.Serializable, Runnable {
	/**
	 * Initialise l'agent lors de son deploiement initial dans le bus a agents mobiles.
	 * @param agentServer le serveur hebergeant initialement l'agent.
	 * @param serverName le nom logique du serveur d'agent
	 */
	public void init(AgentServer agentServer, String serverName);
	/**
	 * Initialise l'agent lors de son deploiement sur un des serveurs du bus.
	 * @param loader le loader attribue pour cet agent
	 * @param server le server actuel pour cet agent
	 * @param serverName le nom logique du serveur d'agent
	 * @throws UnknownHostException 
	 */
	public void init(BAMAgentClassLoader loader, AgentServer server, String serverName);
	/**
	 * ajoute une etape en fin de la feuille de route de l'agent.
	 * @param etape l'etape a ajouter
	 */
	public void addEtape(Etape etape);
}
