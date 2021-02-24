//package network;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.util.ArrayList;
//
//public class WaitingThread extends Thread {
//    ObjectInputStream in;
//    ArrayList<ClientManager> listeners = new ArrayList<>();
//    boolean resetBoard;
//
//    WaitingThread(ObjectInputStream in, boolean resetBoard) {
//        this.in = in;
//        this.resetBoard = resetBoard;
//    }
//
//    public void run() {
//        String msg = null;
//        if (resetBoard) {
//            for (ClientManager l : listeners) {
//                l.gameStart();
//            }
//        } else {
//            do {
//                msg = readMessage(in);
//            }
//            while (msg == null || !(msg.equals("Valid move made")) && !(msg.equals("Check move")) && !(msg.equals("Winning move")));
//
//            for (ClientManager l : listeners) {
//                l.update(msg);
//            }
//        }
//    }
//
//    public void addListener(ClientManager client) {
//        this.listeners.add(client);
//    }
//
//    public String readMessage(ObjectInputStream in) {
//        String msg = null;
//        try {
//            msg = (String) in.readObject();
//        } catch (ClassNotFoundException classNot) {
//            System.err.println("data received in unknown format");
//        } catch (IOException ioException) {
//        }
//        return msg;
//    }
//}
