package pk;

import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Main {

    public void startServers(int serverPort1, int serverPort2) {
        EventQueue.invokeLater(() -> {
            Frame frameServer1 = new Frame(200, false, serverPort1, serverPort2);
            frameServer1.setVisible(true);

            Frame frameServer2 = new Frame(1000, true, serverPort2, serverPort1);
            frameServer2.setVisible(true);

            if (serverPort1 <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid port specified for Server 1.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose();
                return;
            } else {
                System.out.println("Server 1 port: " + serverPort1);
            }

            if (serverPort2 <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid port specified for Server 2.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose();
                return;
            } else {
                System.out.println("Server 2 port: " + serverPort2);
            }
               
          /*  if (!isPortAvailable(serverPort1) ) {// if poert using close frame 1 and 2
                JOptionPane.showMessageDialog(null, "Port " + serverPort1 + " and " + serverPort2 + " are already in use. Cannot start Server 1 and Server 2.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose();
            }

            if (!isPortAvailable(serverPort2)) { // if poert using close frame 1 and 2
                JOptionPane.showMessageDialog(null, "Port " + serverPort2 + " is already in use. Cannot start Server 2.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose();
                return;
            }*/

            UDPServer server1 = new UDPServer(serverPort1); // new object men udp server 
            server1.setMessageListener(message -> frameServer1.appendReceivedMessage(message)); // listnner for msg and if recive msg put in jframe server1 
            new Thread(server1::startServer).start(); // start server 

            UDPServer server2 = new UDPServer(serverPort2);
            server2.setMessageListener(message -> frameServer2.appendReceivedMessage(message));
            new Thread(server2::startServer).start();

            frameServer1.getButtonSend().addActionListener(e -> {// send msg buttun click server 1
                String message = frameServer1.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 1 frame before sending.");
                    return;
                }
                String receiverIP = frameServer1.getReceiverIP();// recive ip recver in jframe 
                int receiverPort = frameServer1.getServerPort();// recive port
                server1.sendMessageTo(message, receiverIP, receiverPort);// send msg 
                takeScreenshot(receiverIP);
            });

            frameServer2.getButtonSend().addActionListener(e -> { // send msg buttun click server 2 
                String message = frameServer2.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 2 frame before sending.");
                    return;
                }
                String receiverIP = frameServer2.getReceiverIP(); 
                int receiverPort = frameServer2.getServerPort(); 
                server2.sendMessageTo(message, receiverIP, receiverPort); 
                takeScreenshot(receiverIP); 
            });
        });
    }
    // if port using return fulse else returrn true
    /*private static boolean isPortAvailable(int port) {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            return true;
        } catch (SocketException e) {
            return false;
        }
    }*/
    //take screen shot if send msg 
    private void takeScreenshot(String receiverIP) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            // Define the path where screenshots will be saved
            String directoryPath = "C:\\Users\\Achraf\\git\\tp_191935030770\\TpGitHub\\screen";
            File screenshotFile = new File(directoryPath, "screen_" + receiverIP + ".png");

            ImageIO.write(screenCapture, "png", screenshotFile);

            System.out.println("Screenshot saved as " + screenshotFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
