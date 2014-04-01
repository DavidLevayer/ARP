package jus.aor.mobilagent.kernel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Define the agent's road map
 * @author  Morat
 */
public class Route implements Iterable<Etape>, Serializable{
	
	private static final long serialVersionUID = 9081294824590167316L;
	
	// List of steps (except last one)
	protected List<Etape> route;
	// Last step of the road map (also the started server)
	protected Etape retour;
	// Indicate if the road map is empty or not
	protected boolean hasNext;
	
	/**
	 * Construction of a road
	 * @param retour  initial (and final) server
	 */
	public Route(Etape retour) {
		route = new LinkedList<Etape>();
		this.retour = retour;
		hasNext=true;
	}
	
	/**
	 * Add a step to the end of a road
	 * @param e the step to add
	 */
	public void add(Etape e) { route.add(route.size(),e);}
	
	/**
	 * Return the next step, or eventually the last one, which is the same as the first one
	 * @return the next step
	 * @throws NoSuchElementException
	 */
	Etape get() throws NoSuchElementException {
		return route.get(0);
	}

	/**
	 * Return the next step (or eventually the last one) and pop it out of the step list.
	 * @return the next step
	 * @throws NoSuchElementException
	 */
	Etape next() throws NoSuchElementException {
		Etape next = route.get(0);
		route.remove(0);
		if(route.size()==0)
			hasNext = false;
		return next;
	}

	/**
	 * Check if it remains a step to go through
	 * @return true if a step is available, false otherwise
	 */
	public boolean hasNext() { return hasNext;}

	public Iterator<Etape> iterator(){return route.iterator();}

	public String toString() {return route.toString().replaceAll(", ","->");}
}
