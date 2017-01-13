package oajava.chat;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Notification extends JFrame {

	private JPanel contentPane;
	private Button b;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notification frame = new Notification(new String[] {"1", "2", "34567890123456789"});
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Notification(String[] lines) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setUndecorated(true);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		// each label 24 high, 6 pixel gap
		// 10 pix borders all around
		
		int mw = 0;
		FontMetrics g = new BufferedImage(1,1,1).getGraphics().getFontMetrics(new Font("Dialog", Font.PLAIN, 26));
		
		for (String s : lines) {
			mw = g.stringWidth(s) > mw ? g.stringWidth(s) : mw;
		}
		
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		setBounds((int) gc.getBounds().getWidth() - mw - 20, (int) gc.getBounds().getHeight() - 100, mw + 20, lines.length * 30 + 50);
		
		for (int i = 0; i < lines.length; i ++) {
			Label label = new Label(lines[i]);
			label.setFont(new Font("Dialog", Font.PLAIN, 26));
			label.setBounds(10, 10 + i * 30, mw, 24);
			contentPane.add(label);
		}
		
		b = new Button("Close");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		b.setBounds(10, getHeight()-40, mw, 30);
		b.setFont(new Font("Dialog", Font.PLAIN, 26));
		contentPane.add(b);
		setAlwaysOnTop(true);
		setVisible(true);
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				animateUp();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				animateDown();
			}

		});
		
		
	}
	
	boolean animating = false;
	
	private void animateUp() {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		if (animating) return;
		animating = true;
		new Thread() {
			public void run() {
				while (gc.getBounds().getHeight()-getY() < getHeight() && animating) {
					setBounds(getX(), getY()-1, getWidth(), getHeight());
					
					try {
						Thread.sleep(16);
					} catch (Exception e) {}
				}
				animating = false;
			}
		}.start();
		
	}
	
	private void animateDown() {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		if (animating) return;
		animating = true;
		new Thread() {
			public void run() {
				while (gc.getBounds().getHeight()-getY() > 100) {
					setBounds(getX(), getY()+1, getWidth(), getHeight());
					
					try {
						Thread.sleep(16);
					} catch (Exception e) {}
				}
				animating = false;
			}
		}.start();
		
	}
	
	public Button getButton() {
		return b;
	}
	
}
