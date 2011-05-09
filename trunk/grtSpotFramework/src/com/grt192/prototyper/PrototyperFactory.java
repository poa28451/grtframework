package com.grt192.prototyper;

import com.grt192.prototyper.spot.FreeRangeProtototyper;

/**
 * Provides abstraction for implementations of Prototyper
 * @author ajc
 */
public class PrototyperFactory {

    private static class PrototyperSingleton {

        public static final Prototyper INSTANCE = new FreeRangeProtototyper();
    }

    /** Returns the singleton Prototyper */
    public static Prototyper getInstance() {
        return PrototyperSingleton.INSTANCE;
    }

    /** Automatically prototype with debug */
    public static void debugedStart() {
        Prototyper p = getInstance();
        p.indicateUnprototyped();
        p.setDebug(true);
        int controlType = p.getPrototype();
        p.debug("Prototyper prototyping complete!: " + controlType);
        p.setDebug(false);

    }
}
