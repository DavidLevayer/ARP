package jus.aor.mobilagent.hotel;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;

public class HotelAgent extends Agent {

	private static final long serialVersionUID = 1L;

	private Map<String,Hotel> hotelList;
	private String localisation;

	public HotelAgent(Object... args) throws URISyntaxException {
		super();
		this.className = this.getClass().getName();
		this.localisation = (String) args[0];
		this.hotelList = new HashMap<String,Hotel>();
	}

	protected _Action findHotel = new _Action(){

		private static final long serialVersionUID = 1L;

		public void execute() {
			//Getting access to the HotelService
			try {
				@SuppressWarnings("unchecked")
				_Service<Map<String,Hotel>> s = (_Service<Map<String,Hotel>>) agentServer.getService("Hotels");
				Map<String,Hotel> res = s.call(localisation);
				//Adding hotels listed by the service to the agent personal hotel list
				hotelList.putAll(res);
			} catch (Exception e) {
				// The service may be down:
				// Nothing to do here... let's go the the following server!
				return;
			}
		}
	};

	protected _Action findTelephone = new _Action(){

		private static final long serialVersionUID = 1L;

		public void execute() {
			//Getting access to the HotelService
			try {
				@SuppressWarnings("unchecked")
				_Service<Map<String,Hotel>> s = (_Service<Map<String,Hotel>>) agentServer.getService("Telephones");
				hotelList = s.call(hotelList);
			} catch (Exception e) {
				// The service may be down:
				// Nothing to do here... let's go the the following server!
			}
		}
	};

	protected _Action retour = new _Action() {

		private static final long serialVersionUID = 1L;

		public void execute() {
			//Print the hotel list with associated phone numbers
			
	
			Iterator<Hotel> i = hotelList.values().iterator();
			Hotel h;
			while(i.hasNext()) {
				h = i.next(); 
				System.out.println("Hotel "+h.getName()+ "tel: "+h.getPhoneNumber());
			}				
			System.out.println("End-----------------");
			System.out.println("Hotels available at "+localisation+" ("+hotelList.size()+"):");
			System.out.println("Time to get information: "+(new Date().getTime() - date) + "ms");
		}
	};
}
