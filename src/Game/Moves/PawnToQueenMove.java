package Game.Moves;

import Game.AllianceColor;
import board.Board;
import board.Location;
import board.Square;
import piece.Pawn;
import piece.Queen;
import piece.Tool;

import java.util.Collection;
import java.util.HashMap;

public class PawnToQueenMove extends Move {
    Tool pawn = null;
    public PawnToQueenMove(Location srcLocation, Location dstLocation) {
        super(srcLocation, dstLocation);
    }

    @Override
    public MoveStatus execute(Board board) {
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Tool tool = locationMap.get(srcLocation).getTool();
        if(tool == null) return MoveStatus.ILLEGAL_MOVE;
        this.captureTool = locationMap.get(dstLocation).getTool();
        tool.makeMove(locationMap.get(dstLocation));
        if (captureTool != null) {
            Collection<Tool> enemyTools = captureTool.getColor().equals(AllianceColor.Black) ?
                    board.getBlackTools() : board.getWhiteTools();
            enemyTools.remove(captureTool);
        }
        if (!(tool instanceof Pawn)) return MoveStatus.ILLEGAL_MOVE;
        tool.getSquare().reset();
        pawn = tool;
        Queen queen = new Queen(tool.getSquare(), tool.getColor());
        queen.setSquare(board.getLocationMap().get(dstLocation));
        board.getLocationMap().get(dstLocation).setTool(queen);
        Collection<Tool> tools = tool.getColor().equals(AllianceColor.Black) ?
                board.getBlackTools() : board.getWhiteTools();
        tools.remove(tool);
        tools.add(queen);
        queen.updateMoves(board);
        return MoveStatus.DONE;
    }

    @Override
    public MoveStatus undo(Board board) {
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Tool tool = locationMap.get(dstLocation).getTool();
        if (captureTool == null) locationMap.get(dstLocation).reset();
        if (captureTool != null) {
            Collection<Tool> enemyTools =  captureTool.getColor().equals(AllianceColor.Black) ?
                    board.getBlackTools() : board.getWhiteTools();
            enemyTools.add(captureTool);
            captureTool.setSquare(locationMap.get(dstLocation));
            locationMap.get(dstLocation).setTool(captureTool);
        }
        pawn.setSquare(locationMap.get(srcLocation));
        locationMap.get(srcLocation).setTool(pawn);
        pawn.updateMoves(board);
        Collection<Tool> tools = tool.getColor().equals(AllianceColor.Black) ?
                board.getBlackTools() : board.getWhiteTools();
        tools.remove(tool);
        tools.add(pawn);
        return MoveStatus.DONE;
    }

    @Override
    public String getType() {
        return "Game.Moves.PawnToQueenMove";
    }
}
