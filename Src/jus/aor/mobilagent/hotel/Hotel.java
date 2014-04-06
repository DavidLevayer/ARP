package jus.aor.mobilagent.hotel;

public class Hotel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String phoneNumber;
	private String name;
	private String localisation;
	
	public Hotel(String name) {
		this.name = name;
	}
	
	public Hotel(String name, String localisation) {
		this.name = name;
		this.localisation = localisation;
	}
	
	public Hotel(String name, String localisation, String phoneNumber) {
		this.name = name;
		this.localisation = localisation;
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	
	
}
