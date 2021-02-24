package Game;

import Game.Moves.Move;
import Game.Moves.MoveFactory;
import Player.Player;
import board.*;
import piece.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

//TODO make Game logic interface
public class GameLogic {

    public static List<Location> filterMoves(Board board, Tool tool, List<Location> possibleMoves) {
        HashMap<Location, Square> boardMap = board.getLocationMap();
        // filter invalid board ranges.
        List<Location> validMoves = possibleMoves.stream().filter(boardMap::containsKey)
                .collect(Collectors.toList());

        // check squares Status.
        return validMoves.stream().filter((candidate) -> !boardMap.get(candidate).isOccupied() ||
                !(tool.getColor() == boardMap.get(candidate).getTool().getColor()))
                .collect(Collectors.toList());
    }

    public static void getMoves(List<Location> possibleMoves, Tool tool, Board board, int xDirection, int yDirection) {
        Location pieceLocation = tool.getSquare().getLocation();
        Location currLocation = LocationFactory.build(pieceLocation, xDirection, yDirection);
        HashMap<Location, Square> boardMap = board.getLocationMap();
        while (boardMap.containsKey(currLocation)) {
            Square square = boardMap.get(currLocation);
            if (square.isOccupied()) {
                if (square.getTool().getColor().equals(tool.getColor())) break;
                possibleMoves.add(currLocation);
                break;
            }
            possibleMoves.add(currLocation);
            currLocation = LocationFactory.build(currLocation, xDirection, yDirection);
        }
    }

    public static boolean isCheck(Board board, Player CurrentPlayer) {
        King king = (CurrentPlayer.getColor().equals(AllianceColor.Black)) ?
                board.getBlackKingLocation() : board.getWhiteKingLocation();
        Collection<Tool> enemyTools = (CurrentPlayer.getColor().equals(AllianceColor.Black)) ?
                board.getWhiteTools() : board.getBlackTools();
//        Location KingLocation = king.getSquare().getLocation();
        for (Tool t : enemyTools) {
            t.updateMoves(board);
            // todo - return in case of older version
            if (t.getPossibleMoves().contains(king.getSquare().getLocation())) return true;

            //          for(Move move : t.getPossibleMoves()) if(move.getDstLocation().equals(KingLocation)) return true;
        }
        return false;
    }

    public static boolean isCheckMate(Board board, Player CurrentPlayer) {
        return !canEscape(board, CurrentPlayer);
    }

//    public static boolean canMakeMove(Board board, Location oldLocation, Location newLocation, Player player) {
//        boolean ans = true;
//        HashMap<Location, Square> locationMap = board.getLocationMap();
//        // check if old location have tool.
//        if (locationMap.containsKey(oldLocation) && locationMap.get(oldLocation).isOccupied()) {
//            Tool tool = locationMap.get(oldLocation).getTool();
//            if (!tool.getColor().equals(player.getColor()) || !tool.getPossibleMoves().contains(newLocation))
//                ans = false;
//            Tool enemyTool = locationMap.get(newLocation).getTool();
//            // make the move.
//            tool.makeMove(locationMap.get(newLocation));
//            Collection<Tool> enemyTools = (player.getColor().equals(AllianceColor.Black)) ?
//                    board.getWhiteTools() : board.getBlackTools();
//            if (enemyTool != null) enemyTools.remove(enemyTool);
//            // in case of check,undo the move.
//            if (GameLogic.isCheck(board, player)) ans = false;
//            //undo move
//            if (enemyTool == null) locationMap.get(newLocation).reset();
//            if (enemyTool != null) {
//                enemyTools.add(enemyTool);
//                enemyTool.setSquare(locationMap.get(newLocation));
//                locationMap.get(newLocation).setTool(enemyTool);
//            }
//            tool.setSquare(locationMap.get(oldLocation));
//            locationMap.get(oldLocation).setTool(tool);
//            return ans;
//        }
//        return false;
//
//    }

    public static boolean canMakeMove(Board board, Location src, Location dst, Player player) {
        Move move = MoveFactory.create(src, dst, board);
        boolean ans = true;
        HashMap<Location, Square> locationMap = board.getLocationMap();
        if (locationMap.containsKey(src) && locationMap.get(src).isOccupied()) {
            Tool tool = locationMap.get(src).getTool();
            if (!tool.getColor().equals(player.getColor()) || !tool.getPossibleMoves().contains(dst))
                ans = false;
            // make the move.
            move.execute(board);
//            board.updateToolsMoves();
            // in case of check,undo the move.
            if (GameLogic.isCheck(board, player)) ans = false;
            //undo move
            move.undo(board);
            // todo - not needed? update in update tool.
//            board.updateToolsMoves();
            return ans;
        }
        return false;

    }

    private static boolean canEscape(Board board, Player CurrentPlayer) {
        // todo delete copy if castle not working.
        Board copyOfBoard = new Board(board);
        Collection<Tool> tools = (CurrentPlayer.getColor().equals(AllianceColor.Black)) ?
                board.getBlackTools() : board.getWhiteTools();
        for (Tool tool : tools) {
//            tool.updateMoves(board);
              tool.updateMoves(copyOfBoard);
//            for (Move move : tool.getPossibleMoves())
//                if (canMakeMove(board, move, CurrentPlayer)) return true;
            Location oldLocation = tool.getSquare().getLocation();
            for (Location newLocation : tool.getPossibleMoves()) {
                if (canMakeMove(copyOfBoard, oldLocation, newLocation, CurrentPlayer)) return true;
            }
        }
        return false;
    }

//    // todo - put in pawn
//    public static boolean canBeQueen(Tool tool, Location dstLocation) {
//        return tool instanceof Pawn && tool.getColor().equals(AllianceColor.White) &&
//                dstLocation.getRank() == 7 ||
//                tool.getColor().equals(AllianceColor.Black) &&
//                        dstLocation.getRank() == 0;
//    }

    // todo - delete if we need.
    public static boolean canCastle(King king, Board board, AllianceColor color, int direction) {
        File rookFile, bishopFile, KnightFile, QueenFile;
        rookFile = direction == 1 ? File.H : File.A;
        KnightFile = direction == 1 ? File.G : File.B;
        bishopFile = direction == 1 ? File.F : File.C;
        QueenFile =  File.D;
        int rowNum = color.equals(AllianceColor.Black) ? 7 : 0;
        HashMap<Location, Square> locationMap = board.getLocationMap();
        Square rookSquare = locationMap.get(new Location(rookFile, rowNum));
        Rook rook = rookSquare.isOccupied() && rookSquare.getTool() instanceof Rook ?
                (Rook) rookSquare.getTool() : null;
        if (rook == null) return false;
        boolean needQueen = direction != 1;
        return !king.isCastled() && king.isFirstMove() && rook.isFirstMove() &&
                !locationMap.get(new Location(KnightFile, rowNum)).isOccupied()
                && !locationMap.get(new Location(bishopFile, rowNum)).isOccupied() &&
                (!needQueen || !locationMap.get(new Location(QueenFile, rowNum)).isOccupied());

    }
}
