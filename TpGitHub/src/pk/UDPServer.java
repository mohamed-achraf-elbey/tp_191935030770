package pk;

import java.awt.Desktop;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;

public class UDPServer {

    private int port;
    private MessageListener messageListener;
    private InetAddress clientAddress;
    private int clientPort = -1;
    private DatagramSocket serverSocket;
    

    public UDPServer(int port) {
        this.port = port;
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void startServer() {
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("Server is running on port " + port);

            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                clientAddress = receivePacket.getAddress(); // Capture client's address
                clientPort = receivePacket.getPort();       // Capture client's port

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message: " + message + " from " + clientAddress + ":" + clientPort);
                if (isValidURL(message)) {
                    openWebpage(message);
                }
                if (messageListener != null) {
                    messageListener.onMessageReceived("From " + clientAddress + ": " + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        sendMessageToClient(message);
    }

    private void sendMessageToClient(String responseMessage) {
        try {
            if (clientAddress != null && clientPort != -1) {
                byte[] sendData = responseMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
                System.out.println("Server message sent to client: " + responseMessage);
            } else {
                System.err.println("No client to send the message to.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendMessageTo(String message, String ipAddress, int port) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            serverSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
    
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean isValidURL(String message) {
        // Simple check to see if the message starts with "http://" or "https://"
        return message.startsWith("www.") || message.startsWith("https://");
    }
}