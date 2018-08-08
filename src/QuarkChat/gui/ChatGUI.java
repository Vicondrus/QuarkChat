package QuarkChat.gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextField;

import QuarkChat.encryption.types.EncrType;
import QuarkChat.networking.MessageListener;
import QuarkChat.networking.MessageSender;
import QuarkChat.networking.WritableGUI;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

public class ChatGUI implements WritableGUI {

	public JFrame frmChat;
	protected JTextField ipField;
	protected JTextField sendPort;
	protected JButton sendBtn;
	public JTextField msgBox;
	protected JTextPane chatBox;
	protected DefaultStyledDocument document;
	protected MessageListener listener;
	protected JScrollPane scrollPane;
	protected StyleContext context;
	protected Style style;
	
	
	/* For Crypto Functions */
	protected EncrType crypto;
	protected JMenuBar menuBar;
	protected JMenu mnNewMenu;
	protected JTextField aesKeyField;
	/* -------------------- */
	
	
	/* For Error Handle */
	private ChatGUI chatThis = this;
	protected JMenu ConnexionSettings;
	protected Box horizontalBox;
	protected JLabel receivePortLable;
	public JButton btnConnect;
	protected JTextField listenPort;
	/* ---------------- */
	
	/* Connect button */
	protected MessageListener msgListen;
	/* -------------- */
	
	/* Connexion Settings */
	public boolean uPnPEnable = true;
	protected JCheckBox chckbxUpnp;
	private JSeparator separator;
	private JSeparator separator_1;
	private JCheckBox aesChkBox;
	/* ------------------ */
	
	/* Message I/O */
	protected MessageSender sender;
	/* ---------------- */

	/**
	 *Create the application.
	 **/
	public ChatGUI(EncrType encry_arg) {
		this.crypto = encry_arg;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChat = new JFrame();
		frmChat.setForeground(Color.WHITE);
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 610, 435);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.setResizable(false);
		frmChat.getContentPane().setLayout(new MigLayout("", "[71px][16px][77px][15px][313px][13px][6px][16px][63px]", "[23px][320px][23px]"));
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectButton.btn(chatThis);
			}
		});
		frmChat.getContentPane().add(btnConnect, "cell 0 0");
		
		ipField = new JTextField();
		ipField.setText("localhost");
		frmChat.getContentPane().add(ipField, "cell 2 0 3 1,growx,aligny center");
		ipField.setColumns(10);
		
		sendPort = new JTextField();
		sendPort.setText("4321");
		frmChat.getContentPane().add(sendPort, "cell 6 0 3 1,growx,aligny center");
		sendPort.setColumns(10);
		
		scrollPane = new JScrollPane();
		frmChat.getContentPane().add(scrollPane, "cell 0 1 9 1,grow");
		
		document = new DefaultStyledDocument();
		chatBox = new JTextPane(document);
		chatBox.setEditable(false);
		scrollPane.setViewportView(chatBox);
		
		context = new StyleContext();
        style = context.addStyle("test", null);
        

		
		msgBox = new JTextField();	
		msgBox.setEnabled(false);
		
		msgBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
	            {
					MessageSender sender = new MessageSender(crypto, msgBox.getText(), ipField.getText(), Integer.parseInt(sendPort.getText()), chatThis);
					sendMessage.normalMessage(chatThis, sender);
	            }
			}
		});
		frmChat.getContentPane().add(msgBox, "cell 0 2 7 1,growx,aligny center");
		msgBox.setColumns(10);
		
		sendBtn = new JButton("Send");
		sendBtn.setEnabled(false);
		
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    sender = new MessageSender(crypto, msgBox.getText(), ipField.getText(), Integer.parseInt(sendPort.getText()), chatThis);
				sendMessage.normalMessage(chatThis, sender);
			}
		});
		frmChat.getContentPane().add(sendBtn, "cell 8 2,growx,aligny top");
		
		menuBar = new JMenuBar();
		frmChat.setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Encryption");
		menuBar.add(mnNewMenu);
		
		aesKeyField = new JTextField();
		aesKeyField.setEnabled(false);
		aesKeyField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				crypto.Symmetric.enable("AES", aesKeyField.getText());
			}
		});
		
		aesChkBox = new JCheckBox("AES");
		aesChkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aesChkBox.isSelected())
				{
					aesKeyField.setEnabled(true);
				}
				else
				{
					aesKeyField.setEnabled(false);
				}
			}
		});
		mnNewMenu.add(aesChkBox);
		
		mnNewMenu.add(aesKeyField);
		aesKeyField.setColumns(10);
		
		ConnexionSettings = new JMenu("Connexion Settings");
		menuBar.add(ConnexionSettings);
		
		horizontalBox = Box.createHorizontalBox();
		ConnexionSettings.add(horizontalBox);
		
		receivePortLable = new JLabel("Receive Port");
		receivePortLable.setHorizontalAlignment(SwingConstants.LEFT);
		ConnexionSettings.add(receivePortLable);
		
		listenPort = new JTextField();
		listenPort.setText("8879");
		ConnexionSettings.add(listenPort);
		listenPort.setColumns(10);
		
		separator = new JSeparator();
		ConnexionSettings.add(separator);
		
		separator_1 = new JSeparator();
		ConnexionSettings.add(separator_1);
		
		checkboxuPnP.chkbox(chatThis);
		
		closeFrame.close(chatThis);
	}

	//@Override
	public void write(String s, int i) {
		sendWrite.write(chatThis, i, s);
	}
}
