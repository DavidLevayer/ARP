package jus.aor.mobilagent.classRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.xml.ws.spi.Provider;

import jus.aor.mobilagent.kernelRMI._Service;


public class Supplier implements ISupplier, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private _Service<?> service;

	public Supplier(_Service<?> service) { 
		this.service = service;
		System.out.println("Constructeur Supplier"+service);
	}
	
	public _Service<?> getService(){ return service;}
	/*
	public String toString(){
		System.out.println("Supplier.toString");
		return "Supplier"+service;
	}
	*/
	public String name() throws RemoteException {
		return Provider.JAXWSPROVIDER_PROPERTY;
	}
	
	public _Service<?> service() throws RemoteException {
		return service;
	};

}
