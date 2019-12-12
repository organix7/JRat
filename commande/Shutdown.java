package RAT.commande;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Shutdown {
    static public String shutdown() {
        try {
            return  Inet4Address.getLocalHost().toString()+" is down";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
            return "Computer is down";
    }
}
