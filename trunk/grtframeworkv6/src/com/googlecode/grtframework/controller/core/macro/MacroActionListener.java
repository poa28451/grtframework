package com.googlecode.grtframework.controller.core.macro;

import com.googlecode.grtframework.event.EventListener;

/**
 * Listens to a Macro for when it starts or completes its action.
 * 
 * @author ajc
 * 
 */
public interface MacroActionListener extends EventListener {

	/**
	 * Called when a Macro starts taking action
	 * 
	 * @param evt
	 */
	public void actionStarted(MacroActionEvent evt);

	/**
	 * Called when a Macro finishes taking action
	 * 
	 * @param evt
	 */
	public void actionCompleted(MacroActionEvent evt);

}
