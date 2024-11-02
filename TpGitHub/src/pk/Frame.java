package pk;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
					System.out.println("gg");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 0, 1050, 653);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel text1 = new JLabel("Ip addres sender");
		text1.setForeground(Color.WHITE);
		text1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		text1.setBounds(37, 49, 170, 42);
		panel.add(text1);
		
		JLabel text1_1 = new JLabel("Port server");
		text1_1.setForeground(Color.WHITE);
		text1_1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		text1_1.setBounds(37, 119, 170, 42);
		panel.add(text1_1);
		
		JLabel text1_2 = new JLabel("Ip addres riciver");
		text1_2.setForeground(Color.WHITE);
		text1_2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		text1_2.setBounds(37, 204, 170, 42);
		panel.add(text1_2);
		
		JLabel text1_3 = new JLabel("Msg");
		text1_3.setForeground(Color.WHITE);
		text1_3.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		text1_3.setBounds(37, 270, 170, 42);
		panel.add(text1_3);
		
		JLabel text1_5 = new JLabel("msg recive");
		text1_5.setForeground(Color.WHITE);
		text1_5.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		text1_5.setBounds(37, 443, 170, 42);
		panel.add(text1_5);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(203, 49, 198, 29);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(203, 119, 198, 29);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(203, 209, 198, 29);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setBounds(203, 277, 198, 29);
		panel.add(textField_3);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(215, 456, 500, 80);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(163, 360, 85, 21);
		panel.add(btnNewButton);
	}
}
