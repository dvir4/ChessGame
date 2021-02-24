//package AI;
//
//import Game.Game;
//import Player.Player;
//import piece.Tool;
//import Game.Moves.MoveStatus;
//import Game.AllianceColor;
//import Game.GameLogic;
//import Game.Moves.Move;
//import Game.Moves.MoveFactory;
//
//import java.util.HashSet;
//
//public class MinMax implements MoveStrategy {
//
//    private final BoardEvaluator evaluator;
//    private final int searchDepth;
//
//    public MinMax(final int searchDepth) {
//        evaluator = new StandardBoardEvaluator();
//        this.searchDepth = searchDepth;
//    }
//
//    @Override
//    public Move makeMoveAI(Game game) {
//        final long startTime = System.currentTimeMillis();
//        Move bestMove = null;
//        int highestSeenValue = Integer.MIN_VALUE;
//        int lowestSeenValue = Integer.MAX_VALUE;
//        int currentValue;
//        Player currentPlayer = game.getCurrentPlayer();
//        System.out.println(currentPlayer + " THINKING with depth = " + this.searchDepth);
//        HashSet<Move> moveList = new HashSet<>();
//        for(Tool tool : currentPlayer.getPieces()){
//            for(Move move : tool.getPossibleMoves()){
//                moveList.add(MoveFactory.create(move));
//            }
//        }
//
////        for (Tool tool : currentPlayer.getPieces()) {
//
////            for (Move move : tool.getPossibleMoves()) {
//        for (Move move : moveList) {
//                // create an backup game - optimize
//                MoveStatus moveStatus = game.getMoveManager().makeMove(move, game.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    game.switchTurn();
//                    currentValue = currentPlayer.getColor().equals(AllianceColor.White) ?
//                            min(game, this.searchDepth - 1) :
//                            max(game, this.searchDepth - 1);
//                    move.undo(game.getBoard());
//                    if (currentPlayer.getColor().equals(AllianceColor.White) &&
//                            currentValue >= highestSeenValue) {
//                        highestSeenValue = currentValue;
//                        bestMove = move;
//                        game.getBoard().updateToolsMoves();
//                        game.switchTurn();
//                    } else if (currentPlayer.getColor().equals(AllianceColor.Black) &&
//                            currentValue <= lowestSeenValue) {
//                        lowestSeenValue = currentValue;
//                        bestMove = move;
//                    }
//                }
//            }
////        }
//        System.out.println(System.currentTimeMillis() - startTime);
//        return bestMove;
//    }
//
//    private static boolean isEndGameScenario(Game game) {
//        return GameLogic.isCheckMate(game.getBoard(), game.getCurrentPlayer());
//    }
//
//    private int min(final Game game,
//                    final int depth) {
//        if (depth == 0 || isEndGameScenario(game)) {
//            return this.evaluator.evaluate(game, depth);
//        }
//        Player currentPlayer = game.getCurrentPlayer();
//        int lowestSeenValue = Integer.MAX_VALUE;
//
//        HashSet<Move> moveList = new HashSet<>();
//        for(Tool tool : currentPlayer.getPieces()){
//            for(Move move : tool.getPossibleMoves()){
//                moveList.add(MoveFactory.create(move));
//            }
//        }
//
////        for (Tool tool : currentPlayer.getPieces()) {
////            for (Move move : tool.getPossibleMoves()) {
//        for (Move move : moveList) {
//                // todo - create an backup game - optimize
//                MoveStatus moveStatus = game.getMoveManager().makeMove(move, game.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    game.switchTurn();
//                    final int currentValue = max(game, depth - 1);
//                    move.undo(game.getBoard());
//                    game.getBoard().updateToolsMoves();
//                    game.switchTurn();
//                    game.getBoard().updateToolsMoves();
//                    if (currentValue <= lowestSeenValue) {
//                        lowestSeenValue = currentValue;
//                    }
//                }
//            }
//
////        }
//        return lowestSeenValue;
//    }
//
//    private int max(final Game game,
//                    final int depth) {
//        if (depth == 0 || isEndGameScenario(game)) {
//            return this.evaluator.evaluate(game, depth);
//        }
//        Player currentPlayer = game.getCurrentPlayer();
//        int highestSeenValue = Integer.MIN_VALUE;
//
//        HashSet<Move> moveList = new HashSet<>();
//        for(Tool tool : currentPlayer.getPieces()){
//            for(Move move : tool.getPossibleMoves()){
//                moveList.add(MoveFactory.create(move));
//            }
//        }
//
////        for (Tool tool : currentPlayer.getPieces()) {
////            for (Move move : tool.getPossibleMoves()) {
//        for (Move move : moveList) {
//                // todo - create an backup game - optimize
//                MoveStatus moveStatus = game.getMoveManager().makeMove(move, game.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    game.switchTurn();
//                    final int currentValue = min(game, depth - 1);
//                    move.undo(game.getBoard());
//                    game.getBoard().updateToolsMoves();
//                    game.switchTurn();
//                    game.getBoard().updateToolsMoves();
//                    if (currentValue >= highestSeenValue) {
//                        highestSeenValue = currentValue;
//                    }
//                }
//            }
////        }
//        return highestSeenValue;
//    }
//
//}


//package AI;
//
//        import Game.Game;
//        import Player.Player;
//        import piece.Tool;
//        import Game.Moves.MoveStatus;
//        import Game.AllianceColor;
//        import Game.GameLogic;
//        import Game.Moves.Move;
//
//public class MinMax implements MoveStrategy {
//
//    private final BoardEvaluator evaluator;
//    private final int searchDepth;
//
//    public MinMax(final int searchDepth) {
//        evaluator = new StandardBoardEvaluator();
//        this.searchDepth = searchDepth;
//    }
//
//    @Override
//    public Move makeMoveAI(Game game) {
//        final long startTime = System.currentTimeMillis();
//        Move bestMove = null;
//        int highestSeenValue = Integer.MIN_VALUE;
//        int lowestSeenValue = Integer.MAX_VALUE;
//        int currentValue;
//        Game copyOfGame;
//        Player currentPlayer = game.getCurrentPlayer();
//        System.out.println(currentPlayer + " THINKING with depth = " + this.searchDepth);
//        for (Tool tool : currentPlayer.getPieces()) {
//            for (Move move : tool.getPossibleMoves()) {
//                copyOfGame = new Game(game);
//                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(move, copyOfGame.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    copyOfGame.switchTurn();
//                    currentValue = currentPlayer.getColor().equals(AllianceColor.White) ?
//                            min(new Game(copyOfGame), this.searchDepth - 1) :
//                            max(new Game(copyOfGame), this.searchDepth - 1);
//                    if (currentPlayer.getColor().equals(AllianceColor.White) &&
//                            currentValue >= highestSeenValue) {
//                        highestSeenValue = currentValue;
//                        bestMove = move;
//                    } else if (currentPlayer.getColor().equals(AllianceColor.Black) &&
//                            currentValue <= lowestSeenValue) {
//                        lowestSeenValue = currentValue;
//                        bestMove = move;
//                    }
//                }
//            }
//        }
//        System.out.println(System.currentTimeMillis() - startTime);
//        return bestMove;
//    }
//
//    private static boolean isEndGameScenario(Game game) {
//        return GameLogic.isCheckMate(game.getBoard(), game.getCurrentPlayer());
//    }
//
//    private int min(final Game game,
//                    final int depth) {
//        Game copyOfGame;
//        if (depth == 0 || isEndGameScenario(game)) {
//            return this.evaluator.evaluate(game, depth);
//        }
//        Player currentPlayer = game.getCurrentPlayer();
//        int lowestSeenValue = Integer.MAX_VALUE;
//        for (Tool tool : currentPlayer.getPieces()) {
//            for (Move move : tool.getPossibleMoves()) {
//                // todo - create an backup game - optimize
//                copyOfGame = new Game(game);
//                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(move, game.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    copyOfGame.switchTurn();
//                    final int currentValue = max(new Game(copyOfGame), depth - 1);
//                    if (currentValue <= lowestSeenValue) {
//                        lowestSeenValue = currentValue;
//                    }
//                }
//            }
//
//        }
//        return lowestSeenValue;
//    }
//
//    private int max(final Game game,
//                    final int depth) {
//        Game copyOfGame;
//        if (depth == 0 || isEndGameScenario(game)) {
//            return this.evaluator.evaluate(game, depth);
//        }
//        Player currentPlayer = game.getCurrentPlayer();
//        int highestSeenValue = Integer.MIN_VALUE;
//        for (Tool tool : currentPlayer.getPieces()) {
//            for (Move move : tool.getPossibleMoves()) {
//                copyOfGame = new Game(game);
//                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(move, game.getCurrentPlayer());
//                if (moveStatus.isDone()) {
//                    copyOfGame.switchTurn();
//                    final int currentValue = min(new Game(copyOfGame), depth - 1);
//                    if (currentValue >= highestSeenValue) {
//                        highestSeenValue = currentValue;
//                    }
//                }
//            }
//        }
//        return highestSeenValue;
//    }
//
//}


package AI;

import Game.Game;
import Player.Player;
import Test.Pair;
import board.Location;
import piece.Tool;
import Game.Moves.MoveStatus;
import Game.AllianceColor;
import Game.GameLogic;


public class MinMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;

    public MinMax(final int searchDepth) {
        evaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public Pair<Location, Location> makeMoveAI(Game game) {
        final long startTime = System.currentTimeMillis();
        Location srcLocation = null;
        Location dstLocation = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        Game copyOfGame;
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println(currentPlayer + " THINKING with depth = " + this.searchDepth);
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, copyOfGame.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    currentValue = currentPlayer.getColor().equals(AllianceColor.White) ?
                            min(new Game(copyOfGame), this.searchDepth - 1) :
                            max(new Game(copyOfGame), this.searchDepth - 1);
                    if (currentPlayer.getColor().equals(AllianceColor.White) &&
                            currentValue >= highestSeenValue) {
                        highestSeenValue = currentValue;
                        srcLocation = src;
                        dstLocation = dst;
                    } else if (currentPlayer.getColor().equals(AllianceColor.Black) &&
                            currentValue <= lowestSeenValue) {
                        lowestSeenValue = currentValue;
                        srcLocation = src;
                        dstLocation = dst;
                    }
                }
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        return new Pair<>(srcLocation, dstLocation);
    }

    private static boolean isEndGameScenario(Game game) {
        return GameLogic.isCheckMate(game.getBoard(), game.getCurrentPlayer());
    }

    private int min(final Game game,
                    final int depth) {
        Game copyOfGame;
        if (depth == 0 || isEndGameScenario(game)) {
            return this.evaluator.evaluate(game, depth);
        }
        Player currentPlayer = game.getCurrentPlayer();
        int lowestSeenValue = Integer.MAX_VALUE;
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    final int currentValue = max(new Game(copyOfGame), depth - 1);
                    if (currentValue <= lowestSeenValue) {
                        lowestSeenValue = currentValue;
                    }
                }
            }

        }
        return lowestSeenValue;
    }

    private int max(final Game game,
                    final int depth) {
        Game copyOfGame;
        if (depth == 0 || isEndGameScenario(game)) {
            return this.evaluator.evaluate(game, depth);
        }
        Player currentPlayer = game.getCurrentPlayer();
        int highestSeenValue = Integer.MIN_VALUE;
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                // todo - create an backup game - optimize
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    final int currentValue = min(new Game(copyOfGame), depth - 1);
                    if (currentValue >= highestSeenValue) {
                        highestSeenValue = currentValue;
                    }
                }
            }
        }
        return highestSeenValue;
    }

}

