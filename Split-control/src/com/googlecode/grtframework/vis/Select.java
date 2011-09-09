package com.googlecode.grtframework.vis;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Selection classes
 * 
 * @author ajc
 * 
 */
public class Select {

	/**
	 * Right click selection: toggle on right click
	 * 
	 * @author ajc
	 * 
	 */
	public static class RightClick {

		private static final int MAX_SELECT_DISTANCE = 30;

		/**
		 * Checks if a selectable should toggle state given its position and the
		 * event
		 * 
		 * @param ev
		 * @param position
		 * @return true if should toggle
		 */
		public static boolean shouldToggleSelect(MouseEvent ev, Point position) {

			// first condition: right click
			// second condition: distance to selectable isn't too large
			return ev.getButton() == MouseEvent.BUTTON3
					&& ev.getPoint().distance(position) < MAX_SELECT_DISTANCE;

		}

	}

}
