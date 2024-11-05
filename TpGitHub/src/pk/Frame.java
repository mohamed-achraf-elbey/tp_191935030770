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
    public boolean GG;
    public int serverPort ; 
    public int portServerReciver ;
    private JLabel text1_4;
    @SuppressWarnings("deprecation")
	public Frame(int position, boolean SC , int serverPort , int portServerReciver) {
    	
        GG = SC;
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
        
        text1_4 = new JLabel("server listening on port "+serverPort);
        text1_4.setForeground(new Color(255, 0, 0));
        text1_4.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_4.setBounds(272, 10, 295, 29);
        panel.add(text1_4);

        setComponentsState(true);
        
    }

    private void addLabels(JPanel panel) {
        JLabel text1 = new JLabel("IP Address Sender");
        text1.setForeground(Color.WHITE);
        text1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1.setBounds(37, 49, 170, 42);
        panel.add(text1);

        JLabel text1_1 = new JLabel("Port Server");
        text1_1.setForeground(Color.WHITE);
        text1_1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_1.setBounds(37, 119, 170, 42);
        panel.add(text1_1);

        JLabel text1_2 = new JLabel("IP Address Receiver");
        text1_2.setForeground(Color.WHITE);
        text1_2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_2.setBounds(37, 189, 170, 42);
        panel.add(text1_2);

        JLabel text1_3 = new JLabel("Message to Send");
        text1_3.setForeground(Color.WHITE);
        text1_3.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_3.setBounds(37, 270, 170, 42);
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
        textFieldSenderIP.setBounds(203, 49, 198, 29);
        panel.add(textFieldSenderIP);

        textFieldPort = new JTextField();
        textFieldPort.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPort.setBounds(203, 119, 198, 29);
        panel.add(textFieldPort);

        textFieldReceiverIP = new JTextField();
        textFieldReceiverIP.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldReceiverIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldReceiverIP.setBounds(203, 194, 198, 29);
        panel.add(textFieldReceiverIP);

        textFieldMessage = new JTextField();
        textFieldMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldMessage.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldMessage.setBounds(203, 263, 476, 68);
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
        btnSend.setBounds(163, 360, 100, 30);
        panel.add(btnSend);

        localhost = new JButton("localhost");
        localhost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    String localIp = localHost.getHostAddress();
                    if (GG) {
                        textFieldPort.setText(""+portServerReciver);
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    } else {
                        textFieldPort.setText(""+portServerReciver);
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
            }
        });

        localhost.setBounds(651, 27, 100, 30);
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
        if(message.isEmpty()&& GG) {
        	showErrorDialog("Please type a message in Server 1");
            return false;
        }
        
        if(message.isEmpty()&& !GG) {
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

    private boolean isValidIPAddress(String ip) {
        String ipPattern = 
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    private void showErrorDialog(String message) {
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


	public int getServerPort() {
		return Integer.parseInt(textFieldPort.getText());
	}
    
    
}
