package QuarkChat.errorhandle;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogFile{
	public static Logger logger = Logger.getLogger("ErrorLogs");  

	public static void Settings()
	{
		try {  
	        // This block configure the logger with handler and formatter  
	        FileHandler fileSettings = new FileHandler("./ErrorLogs.log");  
	        fileSettings.setFormatter(new SimpleFormatter());
	        
	        logger.addHandler(fileSettings); 

	    } catch (SecurityException error) {  
	    	error.printStackTrace();  
	    } catch (IOException error) {  
	    	error.printStackTrace();  
	    }  
	}
}
