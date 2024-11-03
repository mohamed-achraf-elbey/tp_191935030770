package pk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    private int port;
    private MessageListener messageListener;
    private InetAddress clientAddress;
    private int clientPort = -1; // Default value to check if a client is available
    private DatagramSocket serverSocket; // Single server socket for reuse

    // Constructor to initialize server port
    public UDPServer(int port) {
        this.port = port;
    }

    // Method to set the message listener interface
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    // Start server method that listens for incoming messages
    public void startServer() {
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("Server is running on port " + port);

            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // Capture client address and port for response
                clientAddress = receivePacket.getAddress();
                clientPort = receivePacket.getPort();

                // Decode the received message
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message: " + message + " from " + clientAddress + ":" + clientPort);

                if (messageListener != null) {
                    messageListener.onMessageReceived("From " + clientAddress + ": " + message);
                }

                // Send a response message back to the client
                sendMessageToClient("Server message sent to client: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Public method to send a message to the last known client address and port
    public void sendMessage(String message) {
        if (clientAddress != null && clientPort != -1) {
            sendMessageToClient(message);
        } else {
            System.err.println("No client to send the message to.");
        }
    }

    // Private method to send a message to the client
    private void sendMessageToClient(String responseMessage) {
        try {
            // Check if client address and port are available
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

    // Interface for the message listener, to notify when a message is received
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
