package Game.Moves;

import Game.AllianceColor;
import board.Board;
import board.Location;
import board.Square;
import piece.King;
import piece.Rook;
import piece.Tool;

import java.util.Collection;
import java.util.HashMap;

public class NormalMove extends Move {
    private boolean isFirstMove = false;

    public NormalMove(Location srcLocation, Location dstLocation) {
        super(srcLocation, dstLocation);
    }

    @Override
    public MoveStatus execute(Board board) {
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Tool tool = locationMap.get(srcLocation).getTool();
        if (tool instanceof King) isFirstMove = ((King) tool).isFirstMove();
        if (tool instanceof Rook) isFirstMove = ((Rook) tool).isFirstMove();
        this.captureTool = locationMap.get(dstLocation).getTool();
        tool.makeMove(locationMap.get(dstLocation));
        if (captureTool != null) {
            Collection<Tool> enemyTools = captureTool.getColor().equals(AllianceColor.Black) ?
                    board.getBlackTools() : board.getWhiteTools();
            enemyTools.remove(captureTool);
        }
        return MoveStatus.DONE;
    }

    @Override
    public MoveStatus undo(Board board) {
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Tool tool = locationMap.get(dstLocation).getTool();
        if (captureTool == null) locationMap.get(dstLocation).reset();
        if (captureTool != null) {
            Collection<Tool> enemyTools = captureTool.getColor().equals(AllianceColor.Black) ?
                    board.getBlackTools() : board.getWhiteTools();
            enemyTools.add(captureTool);
            captureTool.setSquare(locationMap.get(dstLocation));
            locationMap.get(dstLocation).setTool(captureTool);
        }
        tool.setSquare(locationMap.get(srcLocation));
        locationMap.get(srcLocation).setTool(tool);
        if (isFirstMove) {
            if (tool instanceof King) ((King) tool).setFirstMove(true);
            if (tool instanceof Rook) ((Rook) tool).setFirstMove(true);
        }
        return MoveStatus.DONE;
    }

    @Override
    public String getType() {
        return "Game.Moves.NormalMove";
    }


}
