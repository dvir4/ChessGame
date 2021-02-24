package Game.Moves;

import Game.AllianceColor;
import board.Board;
import board.Location;
import board.LocationFactory;
import piece.King;
import piece.Pawn;
import piece.Tool;


public class MoveFactory {
    private static final String NORMAL_MOVE = "Game.Moves.NormalMove";
    private static final String CASTLE_MOVE = "Game.Moves.CastleMove";
    private static final String PAWN_TO_QUEEN_MOVE = "Game.Moves.PawnToQueenMove";
    public static Move create(Location src, Location dst, Board board){
        try {
            String type = NORMAL_MOVE;
            type = isCastleMove(src,dst,board) ? CASTLE_MOVE : type;
            type = canBeQueen(src,dst,board) ? PAWN_TO_QUEEN_MOVE : type;
            Class[] cArg = new Class[2];
            // define move arguments.
            cArg[0] = Location.class;
            cArg[1] = Location.class;
            return (Move) Class.forName(type).getDeclaredConstructor(cArg)
                    .newInstance(LocationFactory.build(src,0,0),
                            LocationFactory.build(dst,0,0));
        } catch (Exception e){
            throw new RuntimeException("Error: Move +" + src + " does not exist.", e);
        }
    }

    private static boolean isCastleMove(Location src,Location dst,Board board){
        return board.getLocationMap().get(src).getTool() instanceof King &&
                Math.abs(dst.getFile().ordinal() - src.getFile().ordinal()) == 2;
    }

    private static boolean canBeQueen(Location src,Location dst,Board board) {
        Tool tool = board.getLocationMap().get(src).getTool();
        return tool instanceof Pawn && (tool.getColor().equals(AllianceColor.White) &&
                dst.getRank() == 7 ||
                tool.getColor().equals(AllianceColor.Black) &&
                        dst.getRank() == 0);
    }

}
