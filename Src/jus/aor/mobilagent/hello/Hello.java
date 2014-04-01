package jus.aor.mobilagent.hello;
import jus.aor.mobilagent.kernel.Route;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jus.aor.mobilagent.kernel.Etape;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;

/**
 * Basic tests class for BAM
 * @author  Morat
 */
public class Hello extends Agent{

	private static final long serialVersionUID = 1L;

	/**
	 * Construction of an hello agent
	 * @param args no arguments required
	 * @throws URISyntaxException 
	 */
	public Hello(Object... args) throws URISyntaxException {
		super();
		this.className = this.getClass().getName();
	}

	/**
	 * Action to achieve on visited servers 
	 */
	protected _Action doIt = new _Action(){

		private static final long serialVersionUID = 1L;

		public void execute() {
			// Print the date, so we can follow the progression of the agent
			Date d = new Date();
			DateFormat f = DateFormat.getDateTimeInstance();
			System.out.println(f.format(d));
		}
	};

	protected _Action retour(){
		return doIt;
	}
}
