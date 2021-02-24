package piece;

import board.Board;
import board.Location;
import board.Square;

import java.util.List;

public interface Movable {
    List<Location> getValidMoves(Board board);

    List<Location> getValidMoves(Board board, Square square);

    void makeMove(Square square);

    void updateMoves(Board board);


}
