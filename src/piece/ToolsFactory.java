package piece;

import Game.AllianceColor;
import board.Square;

public class ToolsFactory {
    public static Tool createPiece(Square square, String info){
        String[] tokens = info.split(",");
        try {
            Class[] cArg = new Class[2];
            // define tool arguments.
            cArg[0] = Square.class;
            cArg[1] = AllianceColor.class;
            return (Tool) Class.forName("piece."+tokens[0]).getDeclaredConstructor(cArg)
                    .newInstance(square,AllianceColor.valueOf(tokens[1]));
        } catch (Exception e){
            throw new RuntimeException("Error: piece " + tokens[0] + " does not exist.", e);
        }
    }

}
