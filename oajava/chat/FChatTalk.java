package oajava.chat;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

public class FChatTalk extends JInternalFrame {

	private JTextField textField;
	private JFrame f;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public FChatTalk(String name, JFrame f) {
		super("Send");
		this.f = f;
		initialize(name);
	}

	/**
	 * Initialize the contents of the this.
	 */
	private void initialize(final String name) {
		this.setTitle("Send Message");
		this.setBounds(100, 100, 933, 170);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		
		Button button_2 = new Button("Minimize Frame");
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					// button_2
					for (ActionListener al : button_2.getActionListeners()) {
						al.actionPerformed(new ActionEvent(button_2, 6789, button_2.getActionCommand()));
					}
				}
			}
		});
		textField.setBounds(0, 0, 444, 22);
		this.getContentPane().add(textField);
		textField.setColumns(10);
		
		Button button = new Button("Send");
		
		button.setBounds(0, 81, 444, 24);
		this.getContentPane().add(button);
		
		Button button_1 = new Button("Set Color");
		
		button_1.setBounds(0, 58, 444, 24);
		this.getContentPane().add(button_1);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(0, 28, 49, 24);
		this.getContentPane().add(textField_1);
		
		TextField textField_2 = new TextField();
		textField_2.setBounds(55, 28, 49, 24);
		this.getContentPane().add(textField_2);
		
		TextField textField_3 = new TextField();
		textField_3.setBounds(110, 28, 49, 24);
		this.getContentPane().add(textField_3);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Color c = new Color((Integer.valueOf(("0"+textField_1.getText())) << 16) + (Integer.valueOf("0"+textField_2.getText())<<8) + Integer.valueOf("0"+textField_3.getText()));
					try {
						textField.setText(textField.getText()+"<font color=\"" + Integer.toHexString(c.getRGB() & 0xffffff) + "\">");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					//System.out.println(c.getRed()+";"+c.getGreen()+";"+c.getBlue());
				} catch (Exception ex) {}
			}
		});
		Label label = new Label("User: " + name);
		label.setBounds(165, 28, 279, 24);
		this.getContentPane().add(label);
		
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f.setState(JFrame.ICONIFIED);
				WindowEvent evnt = new WindowEvent(f, -999);
				for (WindowListener wa : f.getWindowListeners()) {
					wa.windowIconified(evnt);
				}
			}
		});
		button_2.setBounds(0, 110, 444, 24);
		getContentPane().add(button_2);
		
		
		
		TextField textField_4 = new TextField();
		textField_4.setText("website");
		textField_4.setBounds(450, 28, 221, 24);
		getContentPane().add(textField_4);
		
		TextField textField_5 = new TextField();
		textField_5.setText("display text");
		textField_5.setBounds(673, 28, 221, 24);
		getContentPane().add(textField_5);
		
		Button button_3 = new Button("hyperlink");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(textField.getText() + "<a href=\"" + textField_4.getText()+"\">" + textField_5.getText()+ "</a>");
			}
		});
		button_3.setBounds(450, 58, 444, 24);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Attempt File Sending");
		button_4.addActionListener(new ActionListener() {
			int port = new Random().nextInt(100)+42000;
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(true);
				jfc.setCurrentDirectory(new File(System.getProperty("user.home")+File.separatorChar+"Desktop"));
				jfc.setDialogTitle("Send Files");
				jfc.showOpenDialog(null);
				File[] toSend  = jfc.getSelectedFiles();
				for (File f : toSend) {
					new Thread() {public void run() {try{
						
					oajava.net.file.FileTransferMain.sendFile(port, f);
					port++;
					}catch (Exception e) {}}}.start();
				}
				try {
					String fileext = "";
					for (File f : toSend) {
						fileext += f.getAbsolutePath().split("[.]")[f.getAbsolutePath().split("[.]").length-1] + "%99";
					}
					textField.setText(textField.getText()+"<a href=\"http://www.oafiletransfer.com/FTP%99" + (port-toSend.length) + "%99" + port + "%99" + InetAddress.getLocalHost().getHostAddress() +"%99" + fileext.substring(0, fileext.length()-1) +"\">"+Initialize.username+" has some files for you!</a>");
				} catch (UnknownHostException e) {
					textField.setText(textField.getText() + " Error: " + e.getMessage());
				}
			}
		});
		button_4.setBounds(450, 81, 444, 24);
		getContentPane().add(button_4);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = new Color(Initialize.namecolor);
				Initialize.c.send(("newmsg <font color="+Initialize.getHTMLString()+">["+Initialize.username+"]</font><font color=\"000000\"> " + textField.getText()+"</font>").getBytes());
				textField.setText("");
			}
		});
		
		for (KeyListener kl : button.getKeyListeners()) {
			button.removeKeyListener(kl);
		}
		for (KeyListener kl : button_1.getKeyListeners()) {
			button_1.removeKeyListener(kl);
		}
		for (KeyListener kl : button_2.getKeyListeners()) {
			button_2.removeKeyListener(kl);
		}
		
		setVisible(true);
		
		
	}
	
	

	public void visualize() {
		this.setVisible(true);
	}
}
