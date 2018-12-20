package QuarkChat.networking;

import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;
import QuarkChat.messageformats.FileFormatR;
import QuarkChat.messageformats.MessageFormatR;
import QuarkChat.networking.upnp.UPnP;
import QuarkChat.encryption.AES;

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
	WritableGUI gui;
	final int MaximumSize = 4 * 1024;

	public MessageListener(ChatGUI gui, int port) {
		this.gui = gui;
		this.port = port;
		try {
			server = new ServerSocket(port);
		} catch (IOException error) {
			LogFile.logger.log(Level.WARNING, "IOException", error);
		}
	}

	private int readBuffer(BufferedInputStream BuffMsg, byte[] ByteData) throws IOException
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

	private byte[] copyData(byte[] d_1, int length) // copy d_1 with fix lenght
	{
		byte[] d_2 = new byte[length];

		for(int i=0; i<length; i++)
		{
			d_2[i] = d_1[i];
		}

		return d_2;
	}

	@Override
	public void run() {
		LogFile.logger.log(Level.INFO, "Connexion has been started!");

		/* --- Open uPnP --- */
		MessageOpenuPnP.open(port);
		/* ----------------- */

		try {
			while((clientSocket = server.accept()) != null) {
				System.out.print("Da");
				InputStream in = clientSocket.getInputStream();
				BufferedInputStream BufferMsg = new BufferedInputStream(in);

				byte[] bufferTemp = new byte[2048];
				BufferMsg.read(bufferTemp);

				if(bufferTemp[0] == 0x34)
				{
					// it is a MESSAGE
					MessageFormatR mesaje = new MessageFormatR(bufferTemp);
					if(mesaje.indigest() != null) {
						String mesaj = new String(mesaje.indigest());
						gui.write(mesaj, 1);
					}else {
						gui.write("Ai primit un mesaj encriptat pe care nu l-ai putut decripta! "
								+ "(cheie sau setari gresite)", 2);
					}
				}
				else if(bufferTemp[0] == 0x35){
					// it is a FILE
					FileFormatR file = new FileFormatR(bufferTemp);
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
