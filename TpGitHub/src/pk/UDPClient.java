package pk;

import java.awt.Desktop;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;

public class UDPClient {

    private String senderIP;
    private String receiverIP;
    private int serverPort;
    private int clientPort; // Fixed port for receiving
    private MessageListener messageListener;
    private DatagramSocket clientSocket; // Maintain single socket for send/receive

    public UDPClient(String senderIP, String receiverIP, int serverPort, int clientPort) {
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.serverPort = serverPort;
        this.clientPort = clientPort;

        try {
            // Bind client socket to fixed port
            this.clientSocket = new DatagramSocket(clientPort, InetAddress.getByName(senderIP));
            System.out.println("Client socket bound to " + senderIP + ":" + clientPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void sendMessage(String message) {
        try {
            InetAddress receiverAddress = InetAddress.getByName(receiverIP);
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, serverPort);

            clientSocket.send(sendPacket); // Send using pre-bound socket
            System.out.println("Message sent from " + senderIP + " to " + receiverIP + ":" + serverPort);

            // Check if the message is a URL and open it in the browser
            if (isValidURL(message)) {
                openWebpage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReceiving() {
        new Thread(() -> {
            try {
                byte[] receiveData = new byte[1024];
                System.out.println("Client receiving on port: " + clientPort);

                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (messageListener != null) {
                        messageListener.onMessageReceived("From Server: " + message);
                        System.out.println("Client received: " + message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Method to validate if a string is a valid URL
    private boolean isValidURL(String message) {
        // Simple check to see if the message starts with "http://" or "https://"
        return message.startsWith("www.") || message.startsWith("https://");
    }

    // Method to open a URL in the default web browser
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Interface for receiving messages
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
