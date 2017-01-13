package oajava.chat;

import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserNameInput {

	private JFrame frame;
	private int color;
	private String name, ip;
	private boolean b = true;

	
	/**
	 * Create the application.
	 */
	public UserNameInput() {
		initialize();
	}
	
	public void waitFor() {
		while (b) try {Thread.sleep(100);} catch (Exception e) {}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setTitle("User Name");
		frame.setBounds(100, 100, 474, 151);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Label label = new Label("User Name");
		label.setBounds(10, 10, 88, 24);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("Display Color");
		label_1.setBounds(10, 40, 88, 24);
		frame.getContentPane().add(label_1);
		
		Label label_2 = new Label("Host IP");
		label_2.setBounds(10, 70, 88, 24);
		frame.getContentPane().add(label_2);
		
		TextField textField = new TextField();
		textField.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
				name = textField.getText();
			}
		});
		textField.setBounds(104, 10, 352, 24);
		frame.getContentPane().add(textField);
		
		TextField textField_1 = new TextField();
		textField_1.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
				String text = textField_1.getText();
				try {
				if (text.contains(",")) {
					if (text.split(",").length==3) {
						textField_1.setBackground(new Color(Integer.valueOf(text.split(",")[0]), Integer.valueOf(text.split(",")[1]), Integer.valueOf(text.split(",")[2])));
						
					}
				} else {
					textField_1.setBackground(new Color(Integer.valueOf(textField_1.getText())));
				}
				} catch (Exception e) {}
				color = textField_1.getBackground().getRed() * 65536 + textField_1.getBackground().getGreen() * 256 + textField_1.getBackground().getBlue();
			}
		});
		textField_1.setBounds(104, 40, 352, 24);
		frame.getContentPane().add(textField_1);
		
		TextField textField_2 = new TextField();
		textField_2.setBounds(104, 70, 352, 24);
		frame.getContentPane().add(textField_2);
		textField_2.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
				ip = textField_2.getText();
			}
		});
		
		Button button = new Button("Log Me In!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				b = false;
			}
		});
		button.setBounds(10, 100, 446, 24);
		frame.getContentPane().add(button);
		frame.setSize(484, 100+button.getHeight()+45);
		frame.setVisible(true);
	}
	
	public int getSelectedColor() {
		return color;
	}
	
	public String getSelectedName() {
		return name;
	}
	
	public String getSelectedIP() {
		return ip;
	}
	
	public void close() {
		frame.setVisible(false);
		frame = null;
	}
}
