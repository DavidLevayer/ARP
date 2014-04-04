package jus.aor.mobilagent.kernel;

/**
 * Define a service who could be integrated by a server
 * The service builder must have the following pattern :
 * <bold>public XXXX(Object...)</bold>
 * @author david
 *
 * @param <T> The type of information delivered by the service
 */
public interface _Service<T> {

	/**
	 * Generic call of a service
	 * @param params parameters of the service
	 * @return result returned by the service
	 * @throws IllegalArgumentException
	 */
	public T call(Object... params) throws IllegalArgumentException;
	
}
