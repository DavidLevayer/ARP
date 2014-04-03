package jus.aor.mobilagent.hello;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;

/**
 * Basic tests class for BAM
 * @author  Morat
 */
public class Hello extends Agent{
	
	private List<String> dateList = new LinkedList<String>();

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
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			System.out.println(f.format(d));
			dateList.add(getServerName()+" at "+f.format(d));
		}
	};

	protected _Action retour = new _Action() {
		
		private static final long serialVersionUID = 1L;

		public void execute() {
			
			System.out.println("HelloAgent: Hi! I've visited servers at following dates:");
			for(int i =0; i<dateList.size();i++)
				System.out.println(dateList.get(i));
			System.out.println("End-----------------");

		}
	};

}
