package Game;

import AI.MinMax;
import Game.Moves.MoveStatus;
import Test.Pair;
import board.Location;
import board.Square;
import piece.Tool;

import java.awt.event.MouseEvent;
import java.util.Collections;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Model {
    private Game game;
    private GameState gameState;
    private MinMax ai;
    private static final int DEPTH = 3;

    public Model() {
        this.game = new Game();
        this.gameState = new GameState(game.getBoard(), game.getCurrentPlayer());
        this.ai = new MinMax(DEPTH);
        // todo - add counter to new player connected.
    }

    public GameState handleMoveRequest(Location tileId, MouseEvent e) {
        if (isRightMouseButton(e)) {
            this.gameState.setSourceTile(null);
            this.gameState.setHumanMovedPiece(null);
        } else if (isLeftMouseButton(e)) {
            // use local variables.
            Square sourceTile = gameState.getSourceTile();
            if (sourceTile == null) {
                gameState.setSourceTile(game.getBoard().getLocationMap().get(tileId));
                gameState.setHumanMovedPiece(gameState.getSourceTile().getTool());
                Tool humanMovesPiece = gameState.getHumanMovedPiece();
                if (humanMovesPiece == null) gameState.setSourceTile(null);
                else {
                    humanMovesPiece.updateMoves(game.getBoard());
                    gameState.setPieceLegalMoves(humanMovesPiece.getPossibleMoves());
                }
            } else {
                gameState.setDestinationTile(game.getBoard().getLocationMap().get(tileId));
                // todo - this is older type makemove()
                MoveStatus moveStatus = game.getMoveManager()
                        .makeMove(sourceTile.getLocation(),
                                gameState.getDestinationTile().getLocation(), game.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    game.switchTurn();
                    gameState.setCurrentPlayer(game.getCurrentPlayer());
                    gameState.setBoard(game.getBoard());
                    if (game.isCheckMate()) {
                        System.out.println("CHECK_MATE");
                    }
                } else System.out.println(moveStatus.toString());
                gameState.setSourceTile(null);
                gameState.setHumanMovedPiece(null);
            }
        }
        gameState.setLastMove(null);
        this.updatePieceLegalMoves();
        return gameState;
    }


    public GameState handleAIMove() {
        Pair<Location, Location> move = ai.makeMoveAI(game);
        if (move.getFirst() == null || move.getSecond() == null) {
            System.out.println("CHECK_MATE");
            return gameState;
        }
        gameState.setLastMove(new Pair<>(move.getFirst(),move.getSecond()));
        game.getMoveManager().makeMove(move.getFirst(), move.getSecond(), game.getCurrentPlayer());
        game.switchTurn();
        gameState.setCurrentPlayer(game.getCurrentPlayer());
        gameState.setBoard(game.getBoard());
        if (game.isCheckMate()) {
            System.out.println("CHECK_MATE");
        }
        return gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void updatePieceLegalMoves() {
        Tool humanMovedPiece = gameState.getHumanMovedPiece();
        if (humanMovedPiece != null && humanMovedPiece.getColor() == gameState.getCurrentPlayer().getColor()) {
            gameState.setPieceLegalMoves(humanMovedPiece.getPossibleMoves());
            return;
        }
        gameState.setPieceLegalMoves(Collections.emptyList());

    }

    public Game getGame() {
        return game;
    }

}
