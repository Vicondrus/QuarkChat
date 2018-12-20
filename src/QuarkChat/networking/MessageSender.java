package QuarkChat.networking;

import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;
import QuarkChat.messageformats.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;


public class MessageSender extends Thread{

	private String hostname;
	private int port;
	private ChatGUI error_handle;

	public MessageSender(String message, String hostname, int port, ChatGUI gui_args) {
		this.hostname = hostname;
		this.port = port;
		this.error_handle = gui_args;
	}

	@Override
	public void run(){
		try {
			// assume for testing that this is a message, not a file
			//MessageFormat message = new MessageFormat(this.message);
			FileFormat file = new FileFormat("test", "anuttta");

			while(file.isFinish() == false) {
				Socket client = new Socket(hostname, port);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				client.getOutputStream().write(file.getData());
				client.close();
			}

		} catch (UnknownHostException error) {
			error_handle.write("[Error] Could not connect to the client! Maybe you have no internet connexion.", 2);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.MessageSender->run", error);
		} catch (IOException error) {
			error_handle.write("[Error] Could not connect to the client port! Maybe the client is listening another port.", 2);
			LogFile.logger.log(Level.WARNING, "chatproject.networking.MessageSender->run", error);
		}
	}


}
