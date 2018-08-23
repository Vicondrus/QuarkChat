package QuarkChat.networking.ftp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import QuarkChat.encryption.AES;
import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;
import QuarkChat.gui.sendWrite;

public class FTPsender extends Thread{
	
	private int port;
	private String hostname;
	private File fileAddress;
	private ChatGUI gui;

	public void send(ChatGUI gui, File fileAddress, int port, String hostname)
	{
		sendWrite.write(gui, 2, "[FTP] The file is sending...");
		LogFile.logger.log(Level.INFO, "[FTP] Sending procedure is enabled, file " + fileAddress.getName() + " from " + fileAddress.getPath());
		
		this.port = port;
		this.hostname = hostname;
		this.fileAddress = fileAddress;
		this.gui = gui;
	}
	
	@Override
	public void run(){
		try {
			Socket client = new Socket(hostname, port);
			FileReader readF = new FileReader(fileAddress);
			
			byte dataRead;
			byte[] bufferRead = new byte[16];
			bufferRead[0] = 2; // it is a file
			
			int index = 0;
			
			
			while((dataRead = (byte) readF.read()) != -1)
			{
				bufferRead[++index] = dataRead;
				
				if(index == 15) // 16 bytes has been read
				{ // not yet implemented AES on this type
					client.getOutputStream().write(bufferRead);
					this.wait(100); // make a pause in order to send a message
				}
			}
		
			client.close(); // close connexions
		} catch (UnknownHostException error) {
			gui.write("[Error] Could not connect to the client! Maybe you have no internet connexion.", 2);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.ftp.FTPsender->run", error);
		} catch (IOException error) {
			gui.write("[Error] Could not connect to the client port! Maybe the client is listening another port.", 2);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.ftp.FTPsender->run", error);
		} catch (InterruptedException error) {
			gui.write("[Error] Thread could not be interrupted, this is a critical thing.", 2);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.ftp.FTPsender->run", error);
		}
	}
}
