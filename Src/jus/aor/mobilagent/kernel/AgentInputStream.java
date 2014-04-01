package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class AgentInputStream extends ObjectInputStream{ 
	
	/** * le classLoader à utiliser */ 
	BAMAgentClassLoader loader; 
	
	AgentInputStream(InputStream is, BAMAgentClassLoader cl) throws IOException {
		super(is); 
		loader = cl;
	} 
	protected Class<?> resolveClass(ObjectStreamClass cl) throws IOException,ClassNotFoundException {
		
		try {
			return loader.loadClass(cl.getName());
		} catch(Exception e) {
			return super.resolveClass(cl);
		}
	} 
}
