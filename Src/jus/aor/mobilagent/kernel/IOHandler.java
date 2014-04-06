package jus.aor.mobilagent.kernel;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import jus.util.Dialogue;

/**
 * Handler for writing on IO support
 * @author Morat
 */
class IOHandler extends Handler {
	private Dialogue io;
	/**
	 * Output realized on an external window
	 */
	{
		io = new Dialogue();
		io.setOut();
	}
	
	
	public void close() throws SecurityException{}
	
	public void flush(){}
	
	public void publish(LogRecord record){io.println(record.getMessage());}
}