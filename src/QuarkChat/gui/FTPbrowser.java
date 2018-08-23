package QuarkChat.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import QuarkChat.networking.ftp.FTPsender;

public class FTPbrowser {
	public static void show(ChatGUI gui)
	{
		gui.browser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.frmTrans.setVisible(true);
				
				File fisier;			    
				if((fisier = gui.browser.getSelectedFile()) != null)
				{
					FTPsender.send(gui, fisier, gui.sendPort.getText().gui.ipField.getText());
				}
				
				gui.frmTrans.setVisible(false);
			}
		});
		
	}
}
