package pk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    private int port;

    public UDPServer(int port) {
        this.port = port;
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
