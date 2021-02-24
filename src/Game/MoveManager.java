package Game;

import Game.Moves.Move;
import Game.Moves.MoveFactory;
import Game.Moves.MoveStatus;
import Player.Player;
import board.*;
import piece.Tool;

import java.util.HashMap;

public class MoveManager {
    // TODO remove board, this is part of game class; and change color to one enum
    private final Board board;

    public MoveManager(Board board) {
        this.board = board;
    }


    public MoveStatus makeMove(Location src, Location dst, Player player) {
        Move move = MoveFactory.create(src, dst, board);
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Location srcLocation = move.getSrcLocation();
        // check if old location have tool.
        if (locationMap.containsKey(srcLocation) && locationMap.get(srcLocation).isOccupied()) {
            Tool tool = locationMap.get(srcLocation).getTool();
            if (!tool.getColor().equals(player.getColor()) || !tool.getPossibleMoves().contains(dst))
                return MoveStatus.ILLEGAL_MOVE;
            // make the move
            MoveStatus moveStatus = move.execute(board);
            if (moveStatus != MoveStatus.DONE) return moveStatus;
//            board.updateToolsMoves();
            // in case of check,undo the move.
            if (GameLogic.isCheck(board, player)) {
                //undo the move
                move.undo(board);
                return MoveStatus.LEAVES_PLAYER_IN_CHECK;
            }
            return MoveStatus.DONE;
        }
        return MoveStatus.ILLEGAL_MOVE;
    }
}
