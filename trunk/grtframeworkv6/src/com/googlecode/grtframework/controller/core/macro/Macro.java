package com.googlecode.grtframework.controller.core.macro;

import com.googlecode.grtframework.controller.core.MacroController;

/**
 * A Macro is an action/behavior assigned to a state(in a state machine).
 * 
 * @author ajc
 * 
 */
public interface Macro {
	/*
	 * May be better to make this an abstract class(events)
	 */

	/**
	 * Commands this Macro to take action.
	 * 
	 * Called by a MacroController when the State machine's state changes to
	 * that of this macro.
	 * 
	 * Causes this Macro to send
	 * {@link MacroActionListener#actionStarted(MacroActionEvent)} events,
	 * indicating that action has started.
	 * 
	 * @see MacroController
	 */
	public void act();

	/**
	 * Causes this Macro to stop taking action, and notify its MacroController
	 * that state has changed.
	 * 
	 * Also causes this Macro to send
	 * {@link MacroActionListener#actionComplete(MacroActionEvent)} events,
	 * indicating that action has completed.
	 */
	public void disable(int state);

}
