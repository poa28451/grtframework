package com.grt192.utils;

/**
 * This class defines a J2ME version of assert, as a way to find bugs.
 * @author ajc
 */
public class Assert {

    /**
     * Flag for using the assertion tool or not.
     * If true, the program will exit on false assertion
     */
    public static final boolean DEBUG = true;
    /**
     * Flag for fatal asserts. 
     */
    public static final boolean FATAL = true;

    /**
     * Print the stack trace and exit.
     * @param why Description of 'exception' to be printed
     */
    private static void printStack(String why) {
        new Throwable(why).printStackTrace();
        System.exit(1);
    }

    /**
     * Asserts a condition: if false, then throw exception and exit.
     * This is useful for debugging for impossible cases
     * @param expression The expression that we want to be true
     * @param why Description of exception to be thrown if called
     */
    public static void that(boolean expression, String why) {
        if (DEBUG && !expression) {
            printStack(why);
        }
    }

    /**
     * Asserts a condition without a reason
     * @param expression The expression that we want to be true
     */
    public static void that(boolean expression) {
        that(expression, "");
    }

    /**
     * Asserts that this line of code should never be reached
     * @param Description of exception to be thrown if called
     */
    public static void shouldNotReachHere(String why) {
        that(false, why);
    }

    /** Asserts that this line of code should never be reached without explanation */
    public static void shouldNotReachHere() {
        that(false, "");
    }

    /** 
     * Asserts a fatal condition: if false, we must throw an exception and exit
     * @param expression The expression that we want to be true
     * @param why Description of exception to be thrown if called
     */
    public static void thatFatal(boolean expression, String why) {
        if (FATAL && !expression) {
            printStack(why);
        }
    }

    /**
     * Asserts a fatal condition: if false, we must throw an exception and exit
     * @param expression The expression that we want to be true
     */
    public static void thatFatal(boolean expression) {
        thatFatal(expression, "");
    }

    /**
     * Fatally asserts that this line of code should never be reached
     * @param Description of exception to be thrown if called
     */
    public static void shouldNotReachHereFatal(String why) {
        thatFatal(false, why);
    }

    /** Fatally Asserts that this line of code should never be reached without explanation */
    public static void shouldNotReachHereFatal() {
        thatFatal(false, "");
    }
}
