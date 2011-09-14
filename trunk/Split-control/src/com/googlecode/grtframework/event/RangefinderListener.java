package com.googlecode.grtframework.event;

import com.googlecode.grtframework.core.EventListener;

public interface RangefinderListener extends EventListener {

	public void distanceChanged(RangefinderEvent ev);

}
