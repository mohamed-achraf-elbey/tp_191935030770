package pk;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldSenderIP;
    private JTextField textFieldPort;
    private JTextField textFieldReceiverIP;
    private JTextField textFieldMessage;
    private JTextArea labelReceivedMessage;
    private JScrollPane scrollPane;
    private JButton btnSend;
    private JButton localhost;
    public boolean SC;
    public JButton connect ;
    public int serverPort ; 
    public int portServerReciver ;
    private JLabel text1_4;
    
    private JTextField textFieldPortLis;
    @SuppressWarnings("deprecation")
	public Frame(int position, boolean SC , int serverPort , int portServerReciver) {
    	
        this.SC = SC;
        this.serverPort = serverPort;
        this.portServerReciver = portServerReciver ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(position, 200, 800, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBounds(0, 0, 1050, 653);
        contentPane.add(panel);
        panel.setLayout(null);

        addLabels(panel);

        addTextFields(panel);

        addButtons(panel);
        
        text1_4 = new JLabel("sevet don't listening");
        text1_4.setForeground(new Color(255, 0, 0));
        text1_4.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_4.setBounds(314, 10, 295, 29);
        panel.add(text1_4);
        
        textFieldPortLis = new JTextField();
        textFieldPortLis.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPortLis.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPortLis.setBounds(571, 70, 198, 29);
        panel.add(textFieldPortLis);
        
        JLabel lblPortServer = new JLabel("Port Server listening");
        lblPortServer.setForeground(Color.WHITE);
        lblPortServer.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        lblPortServer.setBounds(411, 64, 170, 42);
        panel.add(lblPortServer);
        
        connect = new JButton("connect");
        
        connect.setEnabled(true);
        connect.setBounds(669, 109, 100, 30);
        panel.add(connect);

        setComponentsState(true);
        
    }

    private void addLabels(JPanel panel) {
        JLabel text1 = new JLabel("IP Address Sender");
        text1.setForeground(Color.WHITE);
        text1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1.setBounds(37, 67, 170, 42);
        panel.add(text1);

        JLabel text1_1 = new JLabel("Port Server reciver");
        text1_1.setForeground(Color.WHITE);
        text1_1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_1.setBounds(37, 158, 170, 42);
        panel.add(text1_1);

        JLabel text1_2 = new JLabel("IP Address Receiver");
        text1_2.setForeground(Color.WHITE);
        text1_2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_2.setBounds(37, 218, 170, 42);
        panel.add(text1_2);

        JLabel text1_3 = new JLabel("Message to Send");
        text1_3.setForeground(Color.WHITE);
        text1_3.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_3.setBounds(37, 300, 170, 42);
        panel.add(text1_3);

        JLabel text1_5 = new JLabel("Message Received");
        text1_5.setForeground(Color.WHITE);
        text1_5.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_5.setBounds(37, 443, 170, 42);
        panel.add(text1_5);
    }

    private void addTextFields(JPanel panel) {
        textFieldSenderIP = new JTextField();
        textFieldSenderIP.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldSenderIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSenderIP.setBounds(203, 67, 198, 29);
        panel.add(textFieldSenderIP);

        textFieldPort = new JTextField();
        textFieldPort.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPort.setBounds(203, 161, 198, 29);
        panel.add(textFieldPort);

        textFieldReceiverIP = new JTextField();
        textFieldReceiverIP.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldReceiverIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldReceiverIP.setBounds(203, 223, 198, 29);
        panel.add(textFieldReceiverIP);

        textFieldMessage = new JTextField();
        textFieldMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldMessage.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldMessage.setBounds(203, 284, 476, 68);
        panel.add(textFieldMessage);

        labelReceivedMessage = new JTextArea();
        labelReceivedMessage.setForeground(Color.WHITE);
        labelReceivedMessage.setBackground(Color.BLACK);
        labelReceivedMessage.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelReceivedMessage.setEditable(false); 

        scrollPane = new JScrollPane(labelReceivedMessage);
        scrollPane.setBounds(215, 456, 500, 80);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);
    }

    private void addButtons(JPanel panel) {
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    textFieldMessage.setText("");
                }
            }
        });
        btnSend.setBounds(370, 379, 100, 30);
        panel.add(btnSend);

        localhost = new JButton("localhost");
        localhost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    String localIp = localHost.getHostAddress();
                    if (SC) {
                        textFieldPort.setText("");
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    } else {
                        textFieldPort.setText("");
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
            }
        });

        localhost.setBounds(33, 26, 100, 30);
        panel.add(localhost);
    }

    public String getSenderIP() {
        return textFieldSenderIP.getText();
    }

    

    public String getReceiverIP() {
        return textFieldReceiverIP.getText();
    }

    public String getMessage() {
        return textFieldMessage.getText();
    }

    public JButton getButtonSend() {
        return btnSend;
    }

    public void appendReceivedMessage(String message) {
        labelReceivedMessage.append(message + "\n");
    }

    public MessageListener getMessageListener() {
        return this::appendReceivedMessage;
    }

    private boolean validateInputs() {
        String senderIP = getSenderIP();
        String receiverIP = getReceiverIP();
        String portText = textFieldPort.getText();
        String message = getMessage();

        if (senderIP.isEmpty() || receiverIP.isEmpty() || portText.isEmpty() ) {
            showErrorDialog("All fields must be filled.");
            return false;
        }
        if(message.isEmpty()&& SC) {
        	showErrorDialog("Please type a message in Server 1");
            return false;
        }
        
        if(message.isEmpty()&& !SC) {
        	showErrorDialog("Please type a message in Server 2");
            return false;
        }

        if (!isValidIPAddress(senderIP)) {
            showErrorDialog("Invalid sender IP address.");
            return false;
        }

        if (!isValidIPAddress(receiverIP)) {
            showErrorDialog("Invalid receiver IP address.");
            return false;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
            if (port < 0 || port > 65535) {
                showErrorDialog("Port number must be between 0 and 65535.");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Port must be a valid number.");
            return false;
        }

        return true; 
    }

    private boolean isValidIPAddress(String ip) { //chek ip use requ
        String ipPattern = 
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    private void showErrorDialog(String message) { //window fpr show error 
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void setComponentsState(boolean enabled) {
        textFieldSenderIP.setEnabled(enabled);
        textFieldReceiverIP.setEnabled(enabled);
        textFieldMessage.setEnabled(enabled);
        btnSend.setEnabled(enabled);
        localhost.setEnabled(enabled);
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
    
    


	public JButton getButtonConnect() {
		return connect;
	}

	private void setConnect(JButton connect) {
		this.connect = connect;
	}

	public int getConnectPort() {
		   try {
		        return Integer.parseInt(textFieldPortLis.getText()); // Get port from textFieldPortLis
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(this, "Invalid port number.", null, JOptionPane.ERROR_MESSAGE);
		        return -1; // Return a default invalid port if an error occurs
		    }	}

	public int getTextFieldPort() {
		   try {
		        return Integer.parseInt(textFieldPort.getText()); // Get port from textFieldPortLis
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(this, "Port listening Error", null, JOptionPane.ERROR_MESSAGE);
		        return -1; // Return a default invalid port if an error occurs
		    }		}

	public void setTextFieldPort(String s) {
		this.textFieldPort.setText(s);;
	}

	public String getTextFieldReceiverIP() {
		return  textFieldReceiverIP.getText();
	}

	public void setTextFieldReceiverIP(JTextField textFieldReceiverIP) {
		this.textFieldReceiverIP = textFieldReceiverIP;
	}

	public JLabel getText1_4() {
		return text1_4;
	}

	public void setText1_4(String s) {
		this.text1_4.setText(s);;
		this.text1_4.setForeground(Color.GREEN);
	}
	
	public void enableAllComponents() {
	    textFieldSenderIP.setEnabled(true);
	    textFieldPort.setEnabled(true);
	    textFieldReceiverIP.setEnabled(true);
	    textFieldMessage.setEnabled(true);
	    btnSend.setEnabled(true);
	    localhost.setEnabled(true);
	    connect.setEnabled(false);
	    textFieldPortLis.setEnabled(false);
	}

	public void disableAllComponents() {
	    textFieldSenderIP.setEnabled(false);
	    textFieldPort.setEnabled(false);
	    textFieldReceiverIP.setEnabled(false);
	    textFieldMessage.setEnabled(false);
	    btnSend.setEnabled(false);
	    localhost.setEnabled(false);
	    //connect.setEnabled(false);
	    //textFieldPortLis.setEnabled(false);
	}
}