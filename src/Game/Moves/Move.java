package Game.Moves;

import board.Board;
import board.Location;
import piece.Tool;

import java.util.Objects;

public abstract class Move {
    protected Location srcLocation;
    protected Location dstLocation;
    protected Tool captureTool;
    protected boolean isExecute = false;

    public Move(Location srcLocation, Location dstLocation) {
        this.srcLocation = srcLocation;
        this.dstLocation = dstLocation;
        captureTool = null;
    }

    public abstract MoveStatus execute(Board board);

    public abstract MoveStatus undo(Board board);

    public Location getDstLocation() {
        return dstLocation;
    }
    public Location getSrcLocation(){
        return srcLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return isExecute == move.isExecute &&
                Objects.equals(srcLocation, move.srcLocation) &&
                Objects.equals(dstLocation, move.dstLocation) &&
                Objects.equals(captureTool, move.captureTool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcLocation, dstLocation, captureTool, isExecute);
    }

    public abstract String getType();
}