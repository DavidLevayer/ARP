package jus.aor.mobilagent.hotel;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import jus.aor.mobilagent.kernel._Service;

public class HotelService implements _Service<Map<String,Hotel>> {

	String xmlFile;
	Map<String,Hotel> hotelList;

	public HotelService(Object... params) {

		hotelList = new HashMap<String,Hotel>();
		//Getting the name of the XML file with the hotel list
		xmlFile = (String) params[0];

		// Parsing the XML file into an hotel list
		DocumentBuilder docBuilder;
		Document doc = null;

		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File(xmlFile));
		} catch (Exception e) {
			System.out.println("Error during the execution of the XMLparser");
			e.printStackTrace();
			e.printStackTrace();
		} 

		NodeList xmlHostelList = doc.getElementsByTagName("Hotel");
		int cpt = 0;
		for(int i=0; i<xmlHostelList.getLength();i++) {
			String localisation = xmlHostelList.item(i).getAttributes().getNamedItem("localisation").getNodeValue();
			String name = xmlHostelList.item(i).getAttributes().getNamedItem("name").getNodeValue();
			cpt++;
			hotelList.put(name,new Hotel(name,localisation));
		}
		System.out.println(cpt+" hotels added");

	}

	public Map<String,Hotel> call(Object... params) throws IllegalArgumentException {
		Map<String,Hotel> specificList = new HashMap<String,Hotel>();
		String localisation = (String) params[0];
		Iterator<Hotel> i = hotelList.values().iterator();
		Hotel h;
		while(i.hasNext()) {
			h = i.next(); 
			if(h.getLocalisation().equals(localisation))
				specificList.put(h.getName(),h);
		}
		return specificList;
	}

}
