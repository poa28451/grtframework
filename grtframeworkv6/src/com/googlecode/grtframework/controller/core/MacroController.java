package com.googlecode.grtframework.controller.core;

import com.googlecode.grtframework.controller.core.macro.Macro;
import com.googlecode.grtframework.controller.core.macro.MacroActionEvent;
import com.googlecode.grtframework.controller.core.macro.MacroActionListener;

/**
 * A MacroController is a state machine, that activates Macros according to its
 * state.
 * 
 * @author ajc
 * 
 */
public class MacroController implements MacroActionListener {
	/**
	 * TODO Unclear if this should be abstract
	 * 
	 * TODO Unclear which package this should be in
	 */
	

	/**
	 * Adds a macro to be called on a specific state
	 * 
	 * @param state
	 * @param m
	 */
	public void addMacro(int state, Macro m){

	}

	/**
	 * Manually establish a state while the controller is offline
	 */
	public void setState(int state) {
		// should halt macro execution and run the macro for this state
	}

	/**
	 * Gets the <code>Macro</code> associated with a provided state
	 * 
	 * @param state
	 *            state macro runs on
	 * @return macro
	 */
	public Macro getMacro(int state) {
		return null;
		// return (Macro) macros.get(new Integer(state));
	}

	/**
	 * True where state is bound to a <code>Macro</code>
	 * 
	 * @param state
	 * @return
	 */
	public boolean isValidState(int state) {
		// return macros.containsKey(new Integer(state));
		return false;
	}

	@Override
	public void startListening() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stopListening() {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionStarted(MacroActionEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCompleted(MacroActionEvent evt) {
		// TODO Auto-generated method stub

	}

}
