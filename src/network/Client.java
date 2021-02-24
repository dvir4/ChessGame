//package network;
//
//
//import board.Location;
//
//import java.awt.event.MouseEvent;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//import static javax.swing.SwingUtilities.isRightMouseButton;
//
//public class Client {
//    private final int DEFAULT_PORT = 8080;
//    private final String DEFAULT_IP = "127.0.0.1";
//    private final int port;
//    private final String ip;
//    private boolean stop = false;
//    private Socket serverSocket;
//    private ObjectOutputStream objectOutput;
//    private ObjectInputStream objectInput;
//
//    public Client(int port, String ip) {
//        this.port = port;
//        this.ip = ip;
//
//        connectServer();
//    }
//
//    public Client() {
//        this.port = DEFAULT_PORT;
//        this.ip = DEFAULT_IP;
//        connectServer();
//    }
//
//    public void connectServer() {
//        try {
//            serverSocket = new Socket(ip, port);
//            objectOutput = new ObjectOutputStream(serverSocket.getOutputStream());
//            objectInput = new ObjectInputStream(serverSocket.getInputStream());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to connect server", e);
//        }
//    }
//
//    public void sendTileCommand(Location tileId, MouseEvent e) {
//        try {
//            String command = convertCommandToString(tileId, e);
//            objectOutput.writeObject(command);
//        } catch (Exception exception) {
//            throw new RuntimeException("cannot open stream", exception);
//        }
//    }
//
//    public void getCurrentGameState(){
//        try {
//            objectOutput.writeObject("getGameState");
//        } catch (Exception exception) {
//            throw new RuntimeException("cannot open stream", exception);
//        }
//    }
//
//    public void closeConnection() throws IOException {
//        try {
//            objectInput.close();
//            objectOutput.close();
//            serverSocket.close();
//        } catch (IOException e) {
//            throw new IOException("cannot close connection", e);
//        }
//
//    }
//
//    private String convertCommandToString(Location tileId, MouseEvent e) {
//        String direction = isRightMouseButton(e) ? ",R" : ",L";
//        return tileId.toString() + direction;
//    }
//
//    public ObjectInputStream getInputSocket(){
//        return this.objectInput;
//    }
//}
