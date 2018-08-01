package QuarkChat.main;

import java.awt.EventQueue;
import java.util.logging.Level;

import QuarkChat.encryption.types.*;
import QuarkChat.errorhandle.LogFile;
import QuarkChat.gui.ChatGUI;

public class Main {

	public static void main(String[] args) {
		/* Encryption modules */
		EncrType encryptModules = new EncrType();
		/* ------------------ */
		
		/* Error handle */
		LogFile.Settings();
		/* -> use LogFile. (it is static)
		/* ------------ */
		
		/* Launch the application*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//encryptModules.disableAll();
					ChatGUI window = new ChatGUI(encryptModules);
					window.frmChat.setVisible(true);
				} catch (Exception error) {
					LogFile.logger.log(Level.SEVERE, "Program could not start properly or it has a fatal error", error);
				}
			}
		});
	}

}
