//package network;
//
//import Game.AllianceColor;
//import Game.GameState;
//import Game.Model;
//import Game.Moves.MoveStatus;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//
//public class ClientHandler extends Observable implements Runnable {
//    private final AllianceColor playerColor;
//    private boolean stop = false;
//    private final Socket clientSocket;
//    private ObjectInputStream objectInput;
//    private ObjectOutputStream objectOutput;
//    private final List<Observer> observers = new ArrayList<>();
//
//
//    public ClientHandler(Socket socket, AllianceColor color) throws IOException {
//        this.clientSocket = socket;
//        this.playerColor = color;
//        try {
//            this.initializeStream();
//        } catch (Exception e) {
//            throw new IOException(e);
//        }
//    }
//
//    private void initializeStream() throws IOException {
//        try {
//            objectInput = new ObjectInputStream(clientSocket.getInputStream());
//            objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
//
//        } catch (Exception e) {
//            throw new IOException("Cannot get stream input", e);
//        }
//
//    }
//
//    @Override
//    public void run() {
//        while (!stop) {
//            try {
//                String command = (String) objectInput.readObject();
//                String[] commandTokens = command.split(",");
//                Command command = CommandFactory.CreateCommand(commandTokens);
//                MoveStatus moveStatus = command.execute();
//                if (moveStatus.isDone()) {
//                    notifyObservers();
//                } else {
//                    sendErrorMsg(moveStatus);
//                }
//                // take this to commandPattern.
//                // get viewModel by singleton.
//                //gameState = Model.getInstance().handleClientRequest(commandTokens[0], commandTokens[1], playerColor);
//            } catch (ClassNotFoundException | IOException e) {
//                throw new RuntimeException(
//                        "cannot recognize class", e);
//            }
//        }
//    }
//
//    public void SendCurrentGameState() throws Exception {
//        try {
//            objectOutput.writeObject(Model.getInstance().getCurrentGameState());
//        } catch (Exception e) {
//            throw new Exception("cannot send MoveStatus", e);
//        }
//    }
//
//    private void sendErrorMsg(MoveStatus moveStatus) throws Exception {
//        try {
//            objectOutput.writeObject(moveStatus);
//        } catch (Exception e) {
//            throw new Exception("cannot send MoveStatus", e);
//        }
//
//    }
//
//    public void addObserver(Observer observer) {
//        observers.add(observer);
//    }
//
//    private void notifyObservers(MoveStatus moveStatus) {
//        observers.forEach(observer -> observer.update(this, moveStatus));
//    }
//
//}
