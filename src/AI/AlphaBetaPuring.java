package AI;

        import Game.Game;
        import Player.Player;
        import Test.Pair;
        import board.Location;
        import piece.Tool;
        import Game.Moves.MoveStatus;
        import Game.AllianceColor;
        import Game.GameLogic;

public class AlphaBetaPuring implements MoveStrategy{
    private final BoardEvaluator evaluator;
    private final int searchDepth;

    public AlphaBetaPuring(final int searchDepth) {
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
                // create an backup game - optimize
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, copyOfGame.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    currentValue = currentPlayer.getColor().equals(AllianceColor.White) ?
                            min(new Game(copyOfGame), this.searchDepth - 1,highestSeenValue,lowestSeenValue) :
                            max(new Game(copyOfGame), this.searchDepth - 1,highestSeenValue,lowestSeenValue);
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
                    final int depth,final int highest,final int lowest) {
        Game copyOfGame;
        if (depth == 0 || isEndGameScenario(game)) {
            return this.evaluator.evaluate(game, depth);
        }
        Player currentPlayer = game.getCurrentPlayer();
        int currentLowest = lowest;
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    currentLowest = Math.min(currentLowest, max(new Game(copyOfGame),
                            depth - 1, highest, currentLowest));
                    if (currentLowest <= highest) {
                        break;
                    }
                }
            }

        }
        return currentLowest;
    }

    private int max(final Game game,
                    final int depth,final int highest,final int lowest) {
        Game copyOfGame;
        if (depth == 0 || isEndGameScenario(game)) {
            return this.evaluator.evaluate(game, depth);
        }
        Player currentPlayer = game.getCurrentPlayer();
        int currentHighest = highest;
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                // create an backup game - optimize
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    currentHighest = Math.max(currentHighest, min(new Game(copyOfGame),
                            depth - 1, currentHighest, lowest));
                    if (lowest <= currentHighest) {
                        break;
                    }
                }
            }
        }
        return currentHighest;
    }
}

