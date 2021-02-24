//package network;
//
//import Game.AllianceColor;
//import Game.Moves.MoveStatus;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//
//public class Server implements Observer {
//    private static final int DEFAULT_PORT = 8080;
//    private static final int PLAYER_LIMIT = 2;
//    private final int port;
//    private ServerSocket serverSocket = null;
//    private boolean stop;
//    private final AllianceColor[] colors = {AllianceColor.Black, AllianceColor.White};
//    private int playerCounter = 0;
//    private final List<ClientHandler> clientHandlers;
//
//    // need to be singleton
//    public Server(int port) {
//        this.port = port;
//        clientHandlers = new ArrayList<>();
//
//    }
//
//    private void openServerSocket() {
//        try {
//            this.serverSocket = new ServerSocket(this.port);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to open port", e);
//        }
//    }
//
//
//    public void runServer() {
//        this.openServerSocket();
//        while (!stop) {
//            try {
//                if (playerCounter == PLAYER_LIMIT) break;
//                System.out.println("waiting for clients");
//                Socket clientSocket = this.serverSocket.accept();
//                // creating new client handler.
//                CreateClientHandler(clientSocket);
//                playerCounter++;
//            } catch (IOException e) {
//                throw new RuntimeException(
//                        "Error accepting client connection", e);
//            }
//        }
//    }
//
//    private void CreateClientHandler(Socket clientSocket) {
//        try {
//            ClientHandler clientHandler = new ClientHandler(clientSocket, colors[playerCounter]);
//            clientHandlers.add(clientHandler);
//            clientHandler.addObserver(this);
//            // running client handler.
//            new Thread(clientHandler) {{
//                start();
//            }};
//        } catch (IOException e) {
//            throw new RuntimeException(
//                    "Error accepting client connection", e);
//        }
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        MoveStatus moveStatus = (MoveStatus) arg;
//        if (moveStatus.isDone()) {
//            for (ClientHandler c : clientHandlers) {
//                try {
//                    c.SendCurrentGameState();
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }
//    }
//}
