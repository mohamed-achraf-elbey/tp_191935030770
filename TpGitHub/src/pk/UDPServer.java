package pk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    private int port;
    private MessageListener messageListener;

    public UDPServer(int port) {
        this.port = port;
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void startServer() {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Server is running on port " + port);

            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress senderAddress = receivePacket.getAddress();
                int senderPort = receivePacket.getPort();

                System.out.println("Received message: " + message + " from " + senderAddress + ":" + senderPort);

                // Update the GUI with the received message
                if (messageListener != null) {
                    messageListener.onMessageReceived("From " + senderAddress + ": " + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Interface for receiving messages
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
