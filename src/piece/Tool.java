package piece;

import board.Board;
import board.Location;
import board.Square;
import Game.AllianceColor;

import java.io.Serializable;
import java.util.HashSet;


public abstract class Tool implements Movable, Serializable {
    protected Square square;
    protected final AllianceColor color;
    protected String name;
//    protected HashSet<Move> possibleMoves = new HashSet<>();
    protected HashSet<Location> possibleMoves;


    public Tool(Square square, AllianceColor color, String name) {
        this.color = color;
        this.name = name;
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public AllianceColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    // not sure if need to be here.
    @Override
    public void makeMove(Square square) {
        this.square.reset();
        square.setTool(this);
        this.square = square;
    }

//    public HashSet<Location> getPossibleMoves(){
//        return this.possibleMoves;
//    }

    public HashSet<Location> getPossibleMoves() {
        return this.possibleMoves;
    }


    public void updateMoves(Board board) {
//        this.possibleMoves.clear();
//        Location srcLocation = this.square.getLocation();
//        for (Location dstLocation : this.getValidMoves(board))
//            this.possibleMoves.add(new NormalMove(srcLocation, dstLocation));
        this.possibleMoves = new HashSet<>(this.getValidMoves(board));
    }

    public abstract int getToolValue();

    public void setPossibleMoves(HashSet<Location> possibleMoves){
        this.possibleMoves = possibleMoves;
    }

//    public Move getMoveByCoordinate(Location srcLocation,Location dstLocation){
//        for(Move move : this.possibleMoves)
//            if(move.getSrcLocation().equals(srcLocation) && move.getDstLocation().equals(dstLocation)) return move;
//        return null;
//    }
}
