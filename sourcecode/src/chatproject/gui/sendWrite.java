package chatproject.gui;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

public class sendWrite {
	public static void write(ChatGUI gui, int i, String message)
	{
		if(i == 1) {
		StyleConstants.setForeground(gui.style, Color.BLUE);
		try {
			gui.document.insertString(gui.document.getLength(), "Them: ", gui.style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StyleConstants.setForeground(gui.style, Color.BLACK);
		try {
			gui.document.insertString(gui.document.getLength(), message + System.lineSeparator(), gui.style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			StyleConstants.setForeground(gui.style, Color.RED);
			try {
				gui.document.insertString(gui.document.getLength(), message + System.lineSeparator(), gui.style);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
