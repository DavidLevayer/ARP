package jus.aor.mobilagent.kernelRMI;

import java.io.Serializable;

/**
 * Define an action who will be executed by an agent
 * @author  Morat
 */
public interface _Action extends Serializable{

	/** empty action */
	public static final _Action NIHIL = new _Action() {

		private static final long serialVersionUID = 1L;
		public void execute() {
			// Empty Action, because it's NIHIL!
		}
	}; 

	/**
	 * execute the action
	 */
	public void execute();
}
