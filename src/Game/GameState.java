package Game;

import Player.Player;
import Test.Pair;
import board.Board;
import board.Location;
import board.Square;
import piece.Tool;


import java.util.Collection;
import java.util.Collections;


public class GameState{
    private Square sourceTile = null;
    private Square destinationTile = null;
    private Tool humanMovedPiece = null;
    private Board board;
    private Player currentPlayer;
    private Collection<Location> pieceLegalMoves = Collections.emptyList();
    private Pair<Location,Location> lastMove;

    public GameState(Board board, Player player) {
        this.board = board;
        this.currentPlayer = player;
    }

    public Square getSourceTile() {
        return sourceTile;
    }

    public void setSourceTile(Square sourceTile) {
        this.sourceTile = sourceTile;
    }

    public Square getDestinationTile() {
        return destinationTile;
    }

    public void setDestinationTile(Square destinationTile) {
        this.destinationTile = destinationTile;
    }

    public Tool getHumanMovedPiece() {
        return humanMovedPiece;
    }

    public void setHumanMovedPiece(Tool humanMovedPiece) {
        this.humanMovedPiece = humanMovedPiece;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Collection<Location> getPieceLegalMoves() {
        return pieceLegalMoves;
    }

    public void setPieceLegalMoves(Collection<Location> pieceLegalMoves) {
        this.pieceLegalMoves = pieceLegalMoves;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Pair<Location, Location> getLastMove() {
        return lastMove;
    }

    public void setLastMove(Pair<Location, Location> lastMove) {
        this.lastMove = lastMove;
    }
}
