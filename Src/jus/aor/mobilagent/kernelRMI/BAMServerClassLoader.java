package jus.aor.mobilagent.kernelRMI;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.jar.JarException;

public class BAMServerClassLoader extends URLClassLoader {

	private HashMap<String, byte[]> classList;
	ClassLoader parent;
	private Jar savedJar;

	public BAMServerClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		this.parent = parent;
		classList = new HashMap<String, byte[]>();
	}

	public void addURL(String s) {
		try {
			addExtraClasses(new Jar(s));
		} catch (JarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addURL (URL url) {
		try {
			addExtraClasses(new Jar(url.getPath()));
		} catch (JarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addExtraClasses (Jar jar) {
		// Creation of an iterator in order to go through jar classes
		this.savedJar = jar;
		
		for(Entry<String, byte[]> elem:jar) {
			// Get the class data (byte form)
			// Cast it into a class
			classList.put(elem.getKey(), elem.getValue());
			
		}
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		Class<?> res;
		
		try {
			res = super.findClass(name);
		} catch(ClassNotFoundException e) {
			
			if(classList.containsKey(name)) {
				byte[] b = classList.get(name);
				res = defineClass(name,b,0,b.length);
			}
			else
				throw new ClassNotFoundException();
		}
		
		return res;
	}
	
	public Jar getSavedJar() {
		return savedJar;
	}

}
