/**
 * Represente un client effectuant une requete lui permettant d'obtenir les numeros de telephone des hotels repondant a son critere de choix.
 * @author  Morat
 */
public class LookForHotel{
	/** le critere de localisaton choisi */
	private String localisation;
	// ...
	/**
	 * Definition de l'objet representant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critere
	 *          de localisation
	 */
	public LookForHotel(String... args){
		localisation = args[0];
	}
	/**
	 * realise une interrogation
	 * @return la duree de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		return 0;
		// ...
	}

	// ...
}
