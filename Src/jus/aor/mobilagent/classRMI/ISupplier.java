package jus.aor.mobilagent.classRMI;

import java.rmi.Remote;
import java.rmi.RemoteException; 

/**
 * @authors Morat, Ginoux, Levayer, Mariage 
 */
public interface ISupplier extends Remote {
	
	/**
	 * send back the service represented by this supplier
	 * @return a service<?>
	 * @throws RemoteException
	 */
	public Object service() throws RemoteException;
	
	/**
	 * send back the name of the service represented by this supplier
	 * @return the name
	 * @throws RemoteException
	 */
	public String name() throws RemoteException;

}