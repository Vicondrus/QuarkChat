package QuarkChat.networking;

import QuarkChat.encryption.types.*;
import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;
import QuarkChat.networking.upnp.UPnP;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;


public class MessageListener extends Thread {

	private ServerSocket server;
	private Socket clientSocket;
	
	int port = 8877;
	ChatGUI gui;
	final int MaximumSize = 4 * 1024;
	EncrType crypto;

	public MessageListener(ChatGUI gui, int port, EncrType encry_args) {
		this.gui = gui;
		this.port = port;
		this.crypto = encry_args;
		try {
			server = new ServerSocket(port);
		} catch (IOException error) {
			LogFile.logger.log(Level.WARNING, "IOException", error);
		}
	}
	

	
	@Override
	public void run() {
		byte[] InputData = new byte[MaximumSize];
		LogFile.logger.log(Level.INFO, "Connexion has been started!");
		
		/* --- Open uPnP --- */
		if(gui.uPnPEnable == true)
		{
			gui.write("Waiting until port forwarding is configurated.... ", 2);
			gui.btnConnect.setEnabled(false);
			
			if(MessageOpenuPnP.open(port))
			{
				gui.write("Port forwarding was succesfully configurated!", 2);
			}
			else
			{
				gui.write("[Error] Port forwarding could not be configurated!", 2);
			}
			
			gui.btnConnect.setEnabled(true);
		}
		/* ----------------- */

		try {
			while((clientSocket = server.accept()) != null) { 
				InputStream in = clientSocket.getInputStream();
				
				
				BufferedInputStream BufferMsg = new BufferedInputStream(in);	
				int BuffSize = MessageChatBox.readBuffer(BufferMsg, InputData); /* Read data */
				
				if(BuffSize == -1) // nothing found
				{
					System.err.println("[Error] Empty message! Maybe your connexion is compromised!");
					return; // force exit
				}
				
				if(InputData[0] == 1) // it is a message
				{
					InputData[0] = 0; // transform in null
					MessageChatBox.showChat(gui, InputData, BuffSize, crypto); // show message on chat
				}
			}
		} catch (IOException error) {
			LogFile.logger.log(Level.FINEST, "IOException", error);
		}
	}
	
	public void closeConnexions()
	{		
		try {
			if(UPnP.isMappedTCP(port))
			{
				UPnP.closePortTCP(port);
			}
			if(clientSocket != null)
			{
				this.clientSocket.close();
			}
			if(server != null)
			{
				this.server.close();
			}
			LogFile.logger.log(Level.INFO, "Connexion has been stopped");
		} catch (IOException error) {
			LogFile.logger.log(Level.WARNING, "chatproject.networking.MessageListener.closeConnexions", error);
		}
	}
}
