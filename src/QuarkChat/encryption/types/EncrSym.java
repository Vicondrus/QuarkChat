package QuarkChat.encryption.types;

/** 
 * Vom memora datele despre encryptia simetrica
 * -: AES = 0 
 * -: DES = 1
 * @author sandu_cristian
 *
 */

public class EncrSym {
	public final int numberEncr = 1; // how many modules of encryption we have implemented
	public String key[] = new String[numberEncr]; // memorize of the used password for encryption and decryption procedures
	public byte inUse[] = new byte[numberEncr]; // memorize of the available encryption modules
	
	public int getID(String name) /** getID will return the ID of the encryption method **/
	{
		if(name == "AES")
		{
			return 0; 
		}
		else
		{
			return -1;
		}
	}
	
	public boolean isEnable(String encryName)
	{
		return inUse[getID(encryName)]==1? true:false;
	}
	
	public void enable(String encryName, String encryPassword) // active a module of encryption
	{
		int ID = getID(encryName);
		
		key[ID] = encryPassword;
		inUse[ID] = 1; // in use
	}
	
	public void disable(String encryName) // disable a module of encryption
	{
		int ID = getID(encryName);
		
		key[ID] = null;
		inUse[ID] = 0; // in not in use anymore
	}
	
	
}
