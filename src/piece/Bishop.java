package piece;

import Game.GameLogic;
import board.Board;
import board.Location;
import board.Square;
import Game.AllianceColor;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Tool {
    public Bishop(Square square,AllianceColor color){
        super(square,color,"Bishop");
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();
        GameLogic.getMoves(possibleMoves,this,board,1,1);
        GameLogic.getMoves(possibleMoves,this,board,-1,1);
        GameLogic.getMoves(possibleMoves,this,board,1,-1);
        GameLogic.getMoves(possibleMoves,this,board,-1,-1);
        return possibleMoves;
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }


    @Override
    public int getToolValue() {
        return 330;
    }
}
