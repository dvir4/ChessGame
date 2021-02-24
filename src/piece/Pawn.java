package piece;

import Game.*;
import board.Board;
import board.Location;
import board.LocationFactory;
import board.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class Pawn extends Tool {
    private static final int WHITE_FIRST_ROW = 1;
    private static final int BLACK_FIRST_ROW = 6;

    public Pawn(Square square, AllianceColor color) {
        super(square, color, "Pawn");
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();
        Location pieceLocation = super.getSquare().getLocation();
        HashMap<Location, Square> boardMap = board.getLocationMap();
        int direction = (super.getColor().equals(AllianceColor.White)) ? 1 : -1;


        possibleMoves.add(LocationFactory.build(pieceLocation, direction, direction));
        possibleMoves.add(LocationFactory.build(pieceLocation, -direction, direction));
        // filter moves in case there is no enemy tool.
        possibleMoves = possibleMoves.stream().filter((candidate) -> boardMap.containsKey(candidate) &&
                boardMap.get(candidate).isOccupied() &&
                !this.getColor().equals(boardMap.get(candidate).getTool().getColor())).collect(Collectors.toList());
        Location firstStep = LocationFactory.build(pieceLocation, 0, direction);
        Location secondStep = LocationFactory.build(pieceLocation, 0, 2 * direction);
        if (isFirstMove() && !boardMap.get(secondStep).isOccupied() && !boardMap.get(firstStep).isOccupied())
            possibleMoves.add(secondStep);
        if (boardMap.get(firstStep) != null && !boardMap.get(firstStep).isOccupied()) possibleMoves.add(firstStep);
        return possibleMoves;
    }

    @Override
    public void makeMove(Square square) {
        this.square.reset();
        square.setTool(this);
        this.square = square;
    }

    @Override
    public int getToolValue() {
        return 100;
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    private boolean isFirstMove() {
        return this.getColor().equals(AllianceColor.White) &&
                this.getSquare().getLocation().getRank() == WHITE_FIRST_ROW ||
                this.getColor().equals(AllianceColor.Black) &&
                        this.getSquare().getLocation().getRank() == BLACK_FIRST_ROW;
    }

//    @Override
//    public void updateMoves(Board board) {
//        this.possibleMoves.clear();
//        Location srcLocation = this.square.getLocation();
//        Move move = null;
//        for (Location dstLocation : this.getValidMoves(board)) {
//            move = GameLogic.canBeQueen(this,dstLocation) ?
//                    new PawnToQueenMove(srcLocation, dstLocation) : new NormalMove(srcLocation, dstLocation);
//            this.possibleMoves.add(move);
//        }
//    }
}
