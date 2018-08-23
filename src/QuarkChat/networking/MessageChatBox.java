package QuarkChat.networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Level;

import QuarkChat.encryption.AES;
import QuarkChat.encryption.types.EncrType;
import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;

public class MessageChatBox {
	
	static protected int readBuffer(BufferedInputStream BuffMsg, byte[] ByteData) throws IOException
	{
		int lengthMsg = 0;
		byte TempBuff;
		
		TempBuff = (byte) BuffMsg.read();
		while(TempBuff != -1)
		{
			ByteData[lengthMsg++] = TempBuff;
			TempBuff = (byte) BuffMsg.read();
		}
		
		return lengthMsg;
	}
	
	private static byte[] copyData(byte[] d_1, int length) // copy d_1 with fix lenght
	{
		byte[] d_2 = new byte[length];
		
		for(int i=0; i<length; i++)
		{
			d_2[i] = d_1[i];
		}
		
		return d_2;
	}
	
	public static void showChat(ChatGUI gui, byte InputData[], int BuffSize, EncrType crypto)
	{
		String line;
		byte[] TempData = copyData(InputData, BuffSize);
		
		if(crypto.Symmetric.isEnable("AES") == true)
		{
			line = AES.decrypt(TempData, crypto.Symmetric.key[0]);
		}
		else
		{
			line = new String(TempData);
		}
		
		if (line != null)
		{
			gui.write(line,1);
		}
		else
		{
			if(AES.isError())
			{
				if(AES.isWrong()) {
					gui.write("[AES encryption] Wrong password!",0);
				}
				else {
					gui.write("[AES encryption] Wrong encryption specifications!", 0);
					LogFile.logger.log(Level.WARNING, "chatproject.networking.MessageListener->run", 
							"Wrong decryption key! The used key ends with: " + 
					crypto.Symmetric.key[0].substring(crypto.Symmetric.key[0].length()-1));

				}
			}
			gui.write("[Error] Error chatproject.networking.94 -> null line.", 0);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.MessageListener->run", "null pointer or null line or bad transmission");
		}
	}
}
