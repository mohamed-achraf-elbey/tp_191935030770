package pk;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;

import javax.imageio.ImageIO;

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

                clientAddress = receivePacket.getAddress();
                clientPort = receivePacket.getPort();

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message: " + message + " from " + clientAddress + ":" + clientPort);

                if (isValidURL(message)) {
                    openWebpage(message);
                }

                if (messageListener != null) {
                    messageListener.onMessageReceived("From " + clientAddress + ": " + message);
                }
                if(message.contains("screen"))
                receiveScreenshot(clientAddress.getHostAddress()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    public void sendMessageTo(String message, String ipAddress, int port) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            System.out.println("Sending message to " + ipAddress + ":" + port + " - " + message);
            if(message.contains("screen"))
            sendScreenshot(ipAddress);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            serverSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendScreenshot(String receiverIP) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);

            String fileName = "C:\\Users\\Achraf\\git\\tp_191935030770\\TpGitHub\\screen\\" + receiverIP + "_send.png";
            File screenshotFile = new File(fileName);

            ImageIO.write(screenshot, "png", screenshotFile);

            System.out.println("Screenshot captured while sending message, saved to: " + screenshotFile.getAbsolutePath());
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            System.err.println("Error capturing screenshot while sending.");
        }
    }

    private void receiveScreenshot(String receiverIP) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);

            // Create a unique file name using the receiver's IP address
            String fileName = "C:\\Users\\Achraf\\git\\tp_191935030770\\TpGitHub\\screen\\" + receiverIP + "_receive.png";
            File screenshotFile = new File(fileName);

            ImageIO.write(screenshot, "png", screenshotFile);

            System.out.println("Screenshot captured while receiving message, saved to: " + screenshotFile.getAbsolutePath());
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            System.err.println("Error capturing screenshot while receiving.");
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
        return message.startsWith("www.") || message.startsWith("https://");
    }

    private void simulateMessageReceived() {
        try {
            Thread.sleep(2000); // Simulating a delay before a message is received
            if (messageListener != null) {
                messageListener.onMessageReceived("Test message received");
                receiveScreenshot("192.168.1.3"); // Example IP address
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
