package Game.Moves;

import Game.AllianceColor;
import board.*;
import piece.King;
import piece.Rook;
import piece.Tool;

public class CastleMove extends Move {
    Square rookLastSquare = null;
    Square rookNewSquare = null;

    public CastleMove(Location srcLocation, Location dstLocation) {
        super(srcLocation, dstLocation);
    }

    @Override
    public MoveStatus execute(Board board) {
        Tool tool = board.getLocationMap().get(srcLocation).getTool();
        if(tool == null) return MoveStatus.ILLEGAL_MOVE;
        King king = tool instanceof King ? (King) tool : null;
        if (king == null) return MoveStatus.ILLEGAL_MOVE;
        int direction = dstLocation.getFile().ordinal() - srcLocation.getFile().ordinal() > 0 ? 1 : -1;
        int rowNum = king.getColor().equals(AllianceColor.Black) ? 7 : 0;
        File rookFile = direction == 1 ? File.H : File.A;
        Tool rook = board.getLocationMap().get(new Location(rookFile, rowNum)).getTool();
        rookLastSquare = rook.getSquare();
        king.makeMove(board.getLocationMap().get(dstLocation));
        rook.makeMove(board.getLocationMap().get(LocationFactory.build(srcLocation, direction, 0)));
        rookNewSquare = rook.getSquare();
        king.castleMove();
        return MoveStatus.DONE;
    }

    @Override
    public MoveStatus undo(Board board) {
        Tool tool = board.getLocationMap().get(dstLocation).getTool();
        tool.makeMove(board.getLocationMap().get(srcLocation));
        Tool anotherTool = rookNewSquare.getTool();
        anotherTool.makeMove(rookLastSquare);
        King king = tool instanceof King ? (King) tool : null;
        king.resetCastled();
        Rook rook = anotherTool instanceof Rook ? (Rook) anotherTool : null;
        rook.setFirstMove(true);
        rookLastSquare = null;
        rookNewSquare = null;
        return MoveStatus.DONE;
    }

    @Override
    public String getType() {
        return "Game.Moves.CastleMove";
    }
}
