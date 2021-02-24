package board;

import piece.Tool;


import java.util.Objects;

public class Square{
    private final SquareColor squareColor;
    private final Location location;
    private boolean occupied;
    private Tool tool;

    public Square(SquareColor color, Location location) {
        this.squareColor = color;
        this.location = location;
        occupied = false;
        tool = null;
    }


    public SquareColor getSquareColor() {
        return squareColor;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void reset() {
        this.occupied = false;
        this.tool = null;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
        this.occupied = true;
    }

    public Tool getTool() {
        return this.tool;
    }

    public void printSquare() {
        location.printLocation();
        if(this.occupied){
            System.out.println(this.tool.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return occupied == square.occupied &&
                squareColor == square.squareColor &&
                Objects.equals(location, square.location) &&
                Objects.equals(tool, square.tool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squareColor, location, occupied, tool);
    }


}
