
package com.grt192.spot.rsh;

/**
 * Interface for generic remote actuation with rsh
 * @author ajc
 */
public interface Commandable {

    public void command(String[] args);
}
