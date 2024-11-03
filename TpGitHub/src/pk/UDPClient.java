package pk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    private String senderIP;
    private String receiverIP;
    private int serverPort;
    private int clientPort; // Fixed port for receiving
    private MessageListener messageListener;

    public UDPClient(String senderIP, String receiverIP, int serverPort, int clientPort) {
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.serverPort = serverPort;
        this.clientPort = clientPort;
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void sendMessage(String message) {
        try (DatagramSocket clientSocket = new DatagramSocket()) { // Dynamically assigned port for sending
            InetAddress receiverAddress = InetAddress.getByName(receiverIP);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, serverPort);

            clientSocket.send(sendPacket);
            System.out.println("Message sent from " + senderIP + " to " + receiverIP + ":" + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReceiving() {
        new Thread(() -> {
            try (DatagramSocket clientSocket = new DatagramSocket(clientPort)) { // Fixed port for receiving
                byte[] receiveData = new byte[1024];
                System.out.println("Client receiving on port: " + clientPort);
                
                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (messageListener != null) {
                        messageListener.onMessageReceived("From Server: " + message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Interface for receiving messages
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
