package QuarkChat.networking;

import java.util.logging.Level;

import QuarkChat.errorhandle.LogFile;
import QuarkChat.networking.upnp.*;

public class MessageOpenuPnP {
	public static boolean open(int PORT)
	{
		if (UPnP.isUPnPAvailable()) { //is UPnP available?
            if (UPnP.isMappedTCP(PORT)) 
            { //is the port already mapped?
               LogFile.logger.log(Level.WARNING, "UPnP port forwarding not enabled: port is already mapped");
            } 
            else if (UPnP.openPortTCP(PORT)) 
            { //try to map port
            	/* wait until port is opened */
            	while(UPnP.isMappedTCP(PORT) != true);
            	/* ------------------------- */
            	
                LogFile.logger.log(Level.INFO, "UPnP port forwarding enabled");
                return true;
            } 
            else 
            {
                LogFile.logger.log(Level.WARNING, "UPnP port forwarding failed");
            }
        } else {
            LogFile.logger.log(Level.WARNING, "UPnP is not available");
        }
		
	    return false;
	}
}
