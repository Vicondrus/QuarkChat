package chatproject.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.xml.bind.DatatypeConverter;

import chatproject.errorhandle.LogFile;

public class SHA_1 {
	public static String fhash(String input) {
	    String SHA_F1 = null;
	    try {
	        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
	        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
	        SHA_F1 = DatatypeConverter.printHexBinary(msdDigest.digest());
	    } catch (UnsupportedEncodingException | NoSuchAlgorithmException error_msg) {
	    	LogFile.logger.log(Level.WARNING, "Error at chatproject.encryption.AES", error_msg);
	    }
	    return SHA_F1;
	}
}
