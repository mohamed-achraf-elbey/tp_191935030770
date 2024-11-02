package pk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    private String senderIP;
    private String receiverIP;
    private int port;

    public UDPClient(String senderIP, String receiverIP, int port) {
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.port = port;
    }

    public void sendMessage(String message) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress receiverAddress = InetAddress.getByName(receiverIP);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, port);

            clientSocket.send(sendPacket);
            System.out.println("Message sent from " + senderIP + " to " + receiverIP + ":" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
