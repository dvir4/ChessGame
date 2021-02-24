//package network;
//
//
//import GUI.View;
//import Game.AllianceColor;
//import Game.GameState;
//
//
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.io.ObjectInputStream;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import board.Location;
//
//
//public class ClientManager {
//    private Client client;
//    private View theView;
//    private AllianceColor color;
//    private GameState currGameState;
//
//    enum state {WAITING, PLAYING}                           //player is in PLAYING state, opponent is in WAITING state
//
//    state playerState;
//
//    public ClientManager() {
//        this.client = new Client();
//        System.out.println("getting board From Server...");
//        new Thread(() -> {
//            GameState gameState = client.getCurrentGameState();
//            theView = new View(gameState);
//            // add button listener to each panel
//            List<View.TilePanel> tilePanels = theView.getTilesPanel();
//            for (View.TilePanel tilePanel : tilePanels) {
//                theView.addTileLisener(tilePanel, new TileListener(tilePanel.getTileId()));
//            }
//        }) {{
//            start();
//        }};
//    }
//
//
//    public void update(Object arg) {
//        if (arg instanceof GameState) {
//            currGameState = (GameState) arg;
//            theView.setGameState(currGameState);
//            theView.drawBoard();
//            assignNextMove();
//        } else if (arg instanceof String) {
//            System.out.println((String) arg);
//        }
//    }
//
//    private void assignNextMove() {
//        if (currGameState.getCurrentPlayer().getColor().equals(color)) {
//            playerState = state.PLAYING;
//        } else {
//            playerState = state.WAITING;
//            waitForNextTurn(client.getInputSocket());
//        }
//    }
//
//    private void waitForNextTurn(ObjectInputStream in) {
//        WaitingThread wt = new WaitingThread(in, false);
//        wt.addListener(this);
//        wt.start();
//    }
//
//    class TileListener implements MouseListener {
//        Location titleId;
//
//        public TileListener(Location location) {
//            this.titleId = location;
//        }
//
//        //todo - client reading all the time data from server, in case there is, invoke ClientManager.
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            if (playerState.equals(state.PLAYING)) {
//                new Thread(() -> {
//                    client.sendTileCommand(titleId, e);
//                }) {{
//                    start();
//                }};
//            }
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//
//        }
//    }
//
//}
//
//
