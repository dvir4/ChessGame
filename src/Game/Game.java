package Game;

import Player.Player;
import Player.WhitePlayer;
import Player.BlackPlayer;
import board.Board;

public class Game {
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private Player currentPlayer;
    private Board board;
    private MoveManager moveManager;

    public Game() {
        this.board = new Board();
        this.whitePlayer = new WhitePlayer(board.getWhiteTools());
        this.blackPlayer = new BlackPlayer(board.getBlackTools());
        currentPlayer = blackPlayer;
        this.moveManager = new MoveManager(board);
    }

    public boolean isCheckMate() {
        return GameLogic.isCheckMate(this.board, this.currentPlayer);
    }

    public WhitePlayer getWhitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getBoard() {
        return this.board;
    }

    public void switchTurn() {
        if (this.currentPlayer.getColor().equals(AllianceColor.Black)) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
    }

    public MoveManager getMoveManager() {
        return this.moveManager;
    }

    // create copy constructor
    public Game(Game g){
        this.board = new Board(g.getBoard());
        this.whitePlayer = new WhitePlayer(board.getWhiteTools());
        this.blackPlayer = new BlackPlayer(board.getBlackTools());
        currentPlayer = g.getCurrentPlayer().getColor().equals(AllianceColor.Black) ? blackPlayer : whitePlayer;
        this.moveManager = new MoveManager(board);
    }

}
