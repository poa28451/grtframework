package com.googlecode.gunncs.grsh;

import com.grt192.prototyper.Prototyper;
import com.grt192.prototyper.PrototyperFactory;
import com.grt192.rsh.RSHClient;

/**
 *
 * @author data
 */
public class ShellMessenger {

    public void sendMessage(String text){
        RSHClient.getClient(PrototyperFactory.getInstance().getHost(0).getAddress()).send(text);
        System.out.println("sending: "+ text);
    }
}
