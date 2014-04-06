package jus.aor.mobilagent.hotelRMI;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import jus.aor.mobilagent.kernelRMI._Service;

public class AnnuaireService implements _Service<Map<String,Hotel>> {

	private static final long serialVersionUID = 1L;
	String xmlFile;
	Map<String,Hotel> hotelList;

	public AnnuaireService(Object... params) {

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

		NodeList xmlHostelList = doc.getElementsByTagName("Telephone");
		int cpt = 0;
		for(int i=0; i<xmlHostelList.getLength();i++) {
			String number = xmlHostelList.item(i).getAttributes().getNamedItem("numero").getNodeValue();
			String name = xmlHostelList.item(i).getAttributes().getNamedItem("name").getNodeValue();
			cpt++;
			Hotel h = new Hotel(name);
			h.setPhoneNumber(number);
			hotelList.put(name,h);
		}
		System.out.println(cpt+" hotels added");

	}

	public Map<String,Hotel> call(Object... params) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Map<String,Hotel> hList = (Map<String,Hotel>) params[0];
		System.out.println("Number of requests: "+hList.size());		
		Iterator<Hotel> i = hList.values().iterator();
		Hotel h;
		while(i.hasNext()) {
			h = i.next(); 			
			h.setPhoneNumber(getNumber(h.getName()));
		}
		
		
		return hList;
	}
	
	private String getNumber(String name) {
		return hotelList.get(name).getPhoneNumber();
	}

}
