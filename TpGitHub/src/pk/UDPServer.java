package pk;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import java.awt.Desktop;

public class UDPServer {

    private int port;
    private MessageListener messageListener;
    private InetAddress clientAddress;
    private int clientPort = -1;
    private DatagramSocket serverSocket;
    private Map<Integer, byte[]> receivedChunks = new HashMap<>();

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

            byte[] receiveData = new byte[64 * 1024]; // Buffer size: 64 KB
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                clientAddress = receivePacket.getAddress();
                clientPort = receivePacket.getPort();

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (messageListener != null && !message.startsWith("chunk:")) {
                    messageListener.onMessageReceived("From " + clientAddress + ": " + message);
                }

                if (isValidURL(message)) {
                    openWebpage(message);
                } else if (message.contains("screen")) {
                    handleScreenshotRequest();
                } else if (message.startsWith("chunk:")) {
                    handleChunkReception(receivePacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    private void handleScreenshotRequest() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);

            List<byte[]> chunks = splitImageToChunks(screenshot);
            for (int i = 0; i < chunks.size(); i++) {
                byte[] chunkData = chunks.get(i);
                String chunkMessage = "chunk:" + i + ":" + chunks.size();
                sendChunk(chunkMessage, chunkData, clientAddress.getHostAddress(), clientPort);
                // Add delay to avoid sending too quickly
                Thread.sleep(200);  // Adjust delay as needed (100ms)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleChunkReception(DatagramPacket receivePacket) {
        try {
            byte[] packetData = receivePacket.getData();
            String packetString = new String(packetData, 0, receivePacket.getLength(), "UTF-8");

            int delimiterIndex = packetString.indexOf("|");
            if (delimiterIndex == -1) {
                throw new IllegalArgumentException("Invalid packet format: no delimiter found");
            }

            String header = packetString.substring(0, delimiterIndex);
            String[] parts = header.split(":");
            int chunkIndex = Integer.parseInt(parts[1]);
            int totalChunks = Integer.parseInt(parts[2]);

            
            byte[] chunkData = Arrays.copyOfRange(packetData, delimiterIndex + 1, receivePacket.getLength());

            
            receivedChunks.put(chunkIndex, chunkData);

            
            if (receivedChunks.size() == totalChunks) {

                
                List<byte[]> chunks = new ArrayList<>();
                for (int i = 0; i < totalChunks; i++) {
                    if (receivedChunks.containsKey(i)) {
                        chunks.add(receivedChunks.get(i));
                    } else {
                        return; 
                    }
                }

                BufferedImage image = combineChunksToImage(chunks);

                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String filename = String.format("screenshot_%s_%d_%s.png", 
                        clientAddress.getHostAddress(), clientPort, timestamp);

                String desktopPath = System.getProperty("user.home") + "/Desktop/screenRR";
                File outputDir = new File(desktopPath);
                if (!outputDir.exists()) outputDir.mkdir();

                File outputFile = new File(outputDir, filename);
                ImageIO.write(image, "png", outputFile);

                System.out.println("Screenshot received and saved: " + outputFile.getAbsolutePath());
                messageListener.onMessageReceived("From " + clientAddress + ": " + "screen save in screenRR");

                receivedChunks.clear(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<byte[]> splitImageToChunks(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();

        List<byte[]> chunks = new ArrayList<>();
        int maxChunkSize = (8 * 1024) - 100; 

        for (int i = 0; i < imageData.length; i += maxChunkSize) {
            int end = Math.min(imageData.length, i + maxChunkSize);
            chunks.add(Arrays.copyOfRange(imageData, i, end));
        }
        return chunks;
    }

    private BufferedImage combineChunksToImage(List<byte[]> chunks) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (byte[] chunk : chunks) {
            baos.write(chunk);
        }
        byte[] imageData = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }

    private void sendChunk(String chunkMessage, byte[] chunkData, String ipAddress, int port) throws IOException {
        InetAddress address = InetAddress.getByName(ipAddress);

        
        byte[] messageHeader = (chunkMessage + "|").getBytes("UTF-8");

        
        if (messageHeader.length + chunkData.length > 8 * 1024) {
            throw new IOException("Chunk size exceeds maximum UDP packet size");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(messageHeader); 
        baos.write(chunkData);     

        byte[] buffer = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        serverSocket.send(packet);
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

    private boolean isValidURL(String message) {
        return message.startsWith("http://") || message.startsWith("https://") || message.startsWith("www.");
    }

    private void openWebpage(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url; 
            }
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to open URL: " + url);
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
    
}
