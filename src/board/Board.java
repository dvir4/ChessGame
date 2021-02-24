package board;


import Game.FileManager;
import piece.King;
import piece.Tool;
import Game.AllianceColor;
import piece.ToolsFactory;



import java.util.*;

public class Board{
    int BOARD_SIZE = 8;
    private final Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
    // get Square according to location.
    private final HashMap<Location, Square> LocationMap;
    private final Collection<Tool> whiteTools;
    private final Collection<Tool> blackTools;
    private King whiteKing;
    private King blackKing;


    public Board() {
        LocationMap = new HashMap<>();
        whiteTools = new HashSet<>();
        blackTools = new HashSet<>();
        HashMap<Location, String> pieceMap = FileManager.loadPiecesMap();
        for (int i = 0; i < BOARD_SIZE; i++) {
            SquareColor color = (i % 2 == 0) ? SquareColor.Black : SquareColor.White;
            int counter = 0;
            for (File file : File.values()) {
                Square square = new Square(color, new Location(file, i));
                board[i][counter] = square;
                LocationMap.put(square.getLocation(), square);
                // create chess piece in case there is one.
                if (pieceMap.containsKey(square.getLocation())) {
                    Tool tool = ToolsFactory.createPiece(square, pieceMap.get(square.getLocation()));
//                    if(tool.getColor().equals(ToolColor.Black)) blackTools.add(tool);
                    if (tool.getColor().equals(AllianceColor.Black)) blackTools.add(tool);
                    else whiteTools.add(tool);
                    square.setTool(tool);
                    // in case of king piece, save in variable
                    if (tool instanceof King) {
                        //if(tool.getColor().equals(ToolColor.Black)) this.blackKing = (King) tool;
                        if (tool.getColor().equals(AllianceColor.Black)) this.blackKing = (King) tool;
                        else this.whiteKing = (King) tool;
                    }
                }

                color = (color == SquareColor.Black) ? SquareColor.White : SquareColor.Black;
                counter += 1;
            }
        }
        // update possible moves for each piece.
        this.updateToolsMoves();
    }

    public void SetToolByLocation(Location loc, Tool tool) {
        this.board[loc.getFile().ordinal()][loc.getRank()].setTool(tool);
    }

    public HashMap<Location, Square> getLocationMap() {
        return this.LocationMap;
    }

    public void printBoard() {
        for (Square[] line : board) {
            for (Square s : line) {
                if (s.isOccupied()) {
                    System.out.print(" " + s.getTool().getName().charAt(0) + " ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.print('\n');
        }
    }

    public Collection<Tool> getWhiteTools() {
        return this.whiteTools;
    }

    public Collection<Tool> getBlackTools() {
        return this.blackTools;
    }

    public King getWhiteKingLocation() {
        return this.whiteKing;
    }

    public King getBlackKingLocation() {
        return this.blackKing;
    }

    public void SaveBoardToFile() {
        //later
    }

    // copy constructor.
    public Board(Board b) {
        this.LocationMap = new HashMap<>();
        whiteTools = new HashSet<>();
        blackTools = new HashSet<>();
        HashMap<Location, Square> locMap = b.getLocationMap();
        locMap.forEach((k, v) -> {
            this.LocationMap.put(k, new Square(v.getSquareColor(), k));
            Square square = LocationMap.get(k);
            board[k.getRank()][k.getFile().ordinal()] = square;
            if (v.isOccupied()) {
                // create new tool
                String toolTokens = v.getTool().getName() + ',' + v.getTool().getColor();
                Tool tool = ToolsFactory.createPiece(square, toolTokens);
                square.setTool(tool);
                if (tool.getColor().equals(AllianceColor.Black)) blackTools.add(tool);
                else whiteTools.add(tool);
                tool.setPossibleMoves(v.getTool().getPossibleMoves());
                if (tool instanceof King) {
                    if (tool.getColor().equals(AllianceColor.Black)) {
                        this.blackKing = (King) tool;
                        if (b.getBlackKingLocation().isCastled()) blackKing.castleMove();
                        if (!b.getBlackKingLocation().isFirstMove()) blackKing.setFirstMove(false);
                    } else {
                        this.whiteKing = (King) tool;
                        if (b.getWhiteKingLocation().isCastled()) whiteKing.castleMove();
                        if (!b.getWhiteKingLocation().isFirstMove()) whiteKing.setFirstMove(false);
                    }
                }
//                if (tool instanceof Rook) {
//                    Rook rook = (Rook) v.getTool();
//                    if (!rook.isFirstMove())
//                        ((Rook) tool).setFirstMove(false);
//                }
            }
        });
        //       this.updateToolsMoves();
    }


    public void updateToolsMoves() {
        // update possible moves for each piece.
        for (Tool tool : this.whiteTools) tool.updateMoves(this);
        for (Tool tool : this.blackTools) tool.updateMoves(this);
    }
}

