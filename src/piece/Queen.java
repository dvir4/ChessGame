package piece;

import Game.GameLogic;
import board.Board;
import board.Location;
import board.Square;
import Game.AllianceColor;
import java.util.ArrayList;
import java.util.List;


public class Queen extends Tool {
    public Queen(Square square,AllianceColor color){
        super(square,color,"Queen");
    }
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();
        for(int col = -1; col <= 1; col++){
            for(int row = -1; row <= 1; row++){
                GameLogic.getMoves(possibleMoves,this,board,col,row);
            }
        }
        return GameLogic.filterMoves(board,this,possibleMoves);
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public int getToolValue() {
        return 900;
    }
}
