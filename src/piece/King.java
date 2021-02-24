package piece;

import Game.*;
import board.Board;
import board.Location;
import board.LocationFactory;
import board.Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class King extends Tool {
    private static final int DIRECTION = 1;
    private boolean isFirstMove = true;
    private boolean isCastled = false;

    public King(Square square, AllianceColor color) {
        super(square, color, "King");
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoves = new ArrayList<>();
        Location pieceLocation = super.getSquare().getLocation();
        for (int col = -1; col <= 1; col++) {
            for (int row = -1; row <= 1; row++) {
                possibleMoves.add(LocationFactory.build(pieceLocation, col, row));
            }
        }
        // TODO- ADD OPTION TO CASTLE MOVE;
        if (GameLogic.canCastle(this, board, color, DIRECTION))
            possibleMoves.add(LocationFactory.build(pieceLocation, 2 * DIRECTION, 0));
        if (GameLogic.canCastle(this, board, color, -DIRECTION))
            possibleMoves.add(LocationFactory.build(pieceLocation, -2 * DIRECTION, 0));
        return GameLogic.filterMoves(board, this, possibleMoves);
    }

    @Override
    public void makeMove(Square square) {
        this.square.reset();
        square.setTool(this);
        this.square = square;
        isFirstMove = false;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public boolean isCastled() {
        return isCastled;
    }

    public void castleMove() {
        isFirstMove = false;
        isCastled = true;
    }

    public void setFirstMove(boolean bool) {
        this.isFirstMove = bool;
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public int getToolValue() {
        return 10000;
    }

//    @Override
//    public void updateMoves(Board board) {
////        this.possibleMoves.clear();
////        Location srcLocation = this.square.getLocation();
////        Move move = null;
////        for (Location dstLocation : this.getValidMoves(board)) {
////            move = !this.isCastled && Math.abs(srcLocation.getFile().ordinal() - dstLocation.getFile().ordinal()) == 2 ?
////                    new CastleMove(srcLocation, dstLocation) : new NormalMove(srcLocation, dstLocation);
////            this.possibleMoves.add(move);
////        }
//        this.possibleMoves = new HashSet<>(this.getValidMoves(board));
//    }

    public void resetCastled(){
        isCastled = false;
        isFirstMove = true;
    }
}
