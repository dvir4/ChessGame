package ViewModel;


import GUI.View;
import Game.GameState;
import Game.Model;
import Game.AllianceColor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import board.Location;


public class ViewModel {
    private final Model theModel;
    private final View theView;


    public ViewModel() {
        theModel = new Model();
        theView = new View(theModel.getGameState());
        // add button listener to each panel
        List<View.TilePanel> tilePanels = theView.getTilesPanel();
        for (View.TilePanel tilePanel : tilePanels) {
            theView.addTileLisener(tilePanel, new TileListener(tilePanel.getTileId()));
        }
    }

    class TileListener implements MouseListener {
        Location titleId;

        public TileListener(Location location) {
            this.titleId = location;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(theModel.getGameState().getCurrentPlayer().getColor().equals(AllianceColor.White)) return;
            GameState gameState = theModel.handleMoveRequest(this.titleId, e);
            new Thread(() ->{
                if(theModel.getGameState().getCurrentPlayer().getColor().equals(AllianceColor.White)){
                    theModel.handleAIMove();
                    theView.setGameState(theModel.getGameState());
                    theView.drawBoard();
                }
            }){{start();}};
            theView.setGameState(gameState);
            theView.drawBoard();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}


