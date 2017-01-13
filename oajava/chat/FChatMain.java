package oajava.chat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

import org.json.JSONArray;

import oajava.net.file.FileTransferMain;

public class FChatMain {

	private JFrame frmChat;
	private JEditorPane htmlbox;
	private FChatTalk fct;
	private JScrollBar sb;
	
	
	/**
	 * Create the application.
	 */
	public FChatMain() {
		initialize();
	}

	public static void main(String[] args) {
		Initialize.main(args);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmChat = new JFrame();
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 450, 407);
		frmChat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container p = (fct = new FChatTalk(Initialize.username, frmChat)).getContentPane();
		p.setPreferredSize(new Dimension(0, fct.getHeight()-30));
		frmChat.getContentPane().setLayout(new BorderLayout(0, 0));
		frmChat.getContentPane().add(p, BorderLayout.SOUTH);
		
		
		JScrollPane scrollPane = new JScrollPane();
		frmChat.getContentPane().add(scrollPane, BorderLayout.CENTER);
		sb = new JScrollBar();
		scrollPane.setVerticalScrollBar(sb);
		sb.setValue(sb.getMaximum());
		htmlbox = new JEditorPane();
		htmlbox.setText("");
		htmlbox.setContentType("text/html");
		htmlbox.setEditable(true);
		htmlbox.setVisible(true);
		scrollPane.setViewportView(htmlbox);
		scrollPane.setVisible(true);
		
		frmChat.setVisible(true);
		frmChat.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				Initialize.c.send(("newmsg <font color="+Initialize.getHTMLString()+">" + Initialize.username + "</font><font color=\"C9D400\"> has left.</font>").getBytes());
				Initialize.c.send(("disconnect " + Initialize.username).getBytes());
				System.exit(0);
			}
			
			boolean iconify = false;
			@Override
			public void windowIconified(WindowEvent e) {
				if (iconify) {return;}
				if (e.getID()==-999) {iconify = true; frmChat.setState(JFrame.ICONIFIED);}
				else frmChat.setState(JFrame.NORMAL);
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				iconify = false;
			}
		});
		htmlbox.setEditable(false);
		htmlbox.setFocusable(false);
		htmlbox.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				for (MouseMotionListener ml : fct.getMouseMotionListeners()) {
					ml.mouseMoved(new MouseEvent(fct, 0, System.currentTimeMillis(), MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 1, 0, false));
				}
			}
		});
		htmlbox.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				if (!arg0.getEventType().equals(EventType.ACTIVATED)) return;
				String data = arg0.getURL().toExternalForm().split("http://www.oafiletransfer.com/")[1];
				
				if (data.startsWith("FTP")) {
					String s = arg0.getURL().toString();
					final int port1 = Integer.valueOf(s.split("%99")[1]);
					final int port2 = Integer.valueOf(s.split("%99")[2]);
					final String ip = s.split("%99")[3];
					final String[] ext = new String[port2-port1+1];
					
					for (int port = port1; port <= port2; port++) {
						ext[port-port1] = s.split("%99")[4+port-port1];
					}
					
					File f = new File(System.getProperty("user.home")+File.separatorChar+"OAJava"+File.separatorChar+"Files");
					f.mkdirs();
					
					for (int port = port1; port <= port2; port++) {
						final int p = port;
						new Thread() {
							public void run() {
								try {
									FileTransferMain.recieveFile(ip, p, new File(f.getAbsolutePath()+File.separatorChar+p+"."+ext[p-port1]));
								} catch (Exception e) {}
							}
						}.start();
					}
					
					try {
						java.awt.Desktop.getDesktop().open(f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						java.awt.Desktop.getDesktop().browse(arg0.getURL().toURI());
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		Initialize.c.send(("newmsg <font color="+Initialize.getHTMLString()+">" + Initialize.username + "</font><font color=\"C9D400\"> has joined.</font>").getBytes());
	}
	
	public void reset(JSONArray master_array) {
		String s = "<html><head></head><body>";
		for (int i = 0; !master_array.isNull(i); i ++) {
			String o = master_array.getString(i);
			
			s+=o+"<br>";
		}
		s += "</body><html>";
		htmlbox.setText(s);
		htmlbox.setContentType("text/html");
		sb.setValues(Integer.MAX_VALUE, sb.getBlockIncrement(), 0, sb.getMaximum());
	}

}
