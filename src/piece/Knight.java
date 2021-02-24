package piece;

import Game.GameLogic;
import board.Board;
import board.Location;
import board.LocationFactory;
import board.Square;
import Game.AllianceColor;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Tool{
    public Knight(Square square,AllianceColor color){
        super(square,color,"Knight");
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();
        Location pieceLocation = super.getSquare().getLocation();
        // get all possible locations and filter them.
        possibleMoves.add(LocationFactory.build(pieceLocation,2,1));
        possibleMoves.add(LocationFactory.build(pieceLocation,2,-1));
        possibleMoves.add(LocationFactory.build(pieceLocation,-2,1));
        possibleMoves.add(LocationFactory.build(pieceLocation,-2,-1));
        possibleMoves.add(LocationFactory.build(pieceLocation,1,2));
        possibleMoves.add(LocationFactory.build(pieceLocation,-1,2));
        possibleMoves.add(LocationFactory.build(pieceLocation,1,-2));
        possibleMoves.add(LocationFactory.build(pieceLocation,-1,-2));
        return GameLogic.filterMoves(board,this,possibleMoves);

    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public int getToolValue() {
        return 300;
    }
}
