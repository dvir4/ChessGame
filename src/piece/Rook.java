package piece;

import Game.GameLogic;
import board.Board;
import board.Location;
import board.LocationFactory;
import board.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Game.AllianceColor;

public class Rook extends Tool{
    private boolean isFirstMove = true;

    public Rook(Square square,AllianceColor color){
        super(square,color,"Rook");
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();

        GameLogic.getMoves(possibleMoves,this,board,1,0);
        GameLogic.getMoves(possibleMoves,this,board,0,1);
        GameLogic.getMoves(possibleMoves,this,board,-1,0);
        GameLogic.getMoves(possibleMoves,this,board,0,-1);
        return possibleMoves;
    }

    @Override
    public void makeMove(Square square) {
        this.square.reset();
        square.setTool(this);
        this.square = square;
        isFirstMove = false;

    }

    public boolean isFirstMove(){
        return isFirstMove;
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public int getToolValue() {
        return 500;
    }

    public void setFirstMove(boolean bool){
        isFirstMove = bool;
    }
}
