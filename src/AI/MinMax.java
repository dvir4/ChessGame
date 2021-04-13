
package AI;

import Game.Game;
import Player.Player;
import Test.Pair;
import board.Location;
import piece.Tool;
import Game.Moves.MoveStatus;
import Game.GameLogic;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


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
        OptionalInt currentValue;
        Game copyOfGame;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println(currentPlayer + " THINKING with depth = " + this.searchDepth);
        for (Tool tool : currentPlayer.getPieces()) {
            Location src = tool.getSquare().getLocation();
            for (Location dst : tool.getPossibleMoves()) {
                copyOfGame = new Game(game);
                MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, copyOfGame.getCurrentPlayer());
                if (moveStatus.isDone()) {
                    copyOfGame.switchTurn();
                    currentValue =  forkJoinPool.invoke(new Min(new Game(copyOfGame), this.searchDepth - 1,this.evaluator));
                    if (currentValue.isPresent() && currentValue.getAsInt() >= highestSeenValue) {
                        highestSeenValue = currentValue.getAsInt();
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
    public class Min extends RecursiveTask<OptionalInt> {
        final Game game;
        final int depth;
        final BoardEvaluator evaluator;

        public Min(Game game,int depth,BoardEvaluator evaluator){
            this.game = game;
            this.depth = depth;
            this.evaluator = evaluator;
        }

        @Override
        protected OptionalInt compute() {
            if (depth == 0 || isEndGameScenario(game)) {
                return OptionalInt.of(this.evaluator.evaluate(game, depth));
            }
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream().map(ForkJoinTask::join).reduce((x, y) -> x.getAsInt() - y.getAsInt()  <= 0  ? x : y).get();
        }

        private Collection<RecursiveTask<OptionalInt>> createSubtasks() {
            List<RecursiveTask<OptionalInt>> dividedTasks = new ArrayList<>();
            Game copyOfGame;
            Player currentPlayer = game.getCurrentPlayer();
            for (Tool tool : currentPlayer.getPieces()) {
                Location src = tool.getSquare().getLocation();
                for (Location dst : tool.getPossibleMoves()) {
                    copyOfGame = new Game(game);
                    MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                    if (moveStatus.isDone()) {
                        copyOfGame.switchTurn();
                        dividedTasks.add(new Max(new Game(copyOfGame), depth - 1,this.evaluator));
                    }
                }
            }
            return dividedTasks;
        }
    }

    public class Max extends RecursiveTask<OptionalInt> {
        final Game game;
        final int depth;
        final BoardEvaluator evaluator;

        public Max(Game game,int depth,BoardEvaluator evaluator){
            this.game = game;
            this.depth = depth;
            this.evaluator = evaluator;
        }

        @Override
        protected OptionalInt compute() {
            if (depth == 0 || isEndGameScenario(game)) {
                return OptionalInt.of(this.evaluator.evaluate(game, depth));
            }
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream().map(ForkJoinTask::join).reduce((x, y) -> x.getAsInt() - y.getAsInt()  >= 0  ? x : y).get();
        }

        private Collection<RecursiveTask<OptionalInt>> createSubtasks() {
            List<RecursiveTask<OptionalInt>> dividedTasks = new ArrayList<>();
            Game copyOfGame;
            Player currentPlayer = game.getCurrentPlayer();
            for (Tool tool : currentPlayer.getPieces()) {
                Location src = tool.getSquare().getLocation();
                for (Location dst : tool.getPossibleMoves()) {
                    copyOfGame = new Game(game);
                    MoveStatus moveStatus = copyOfGame.getMoveManager().makeMove(src, dst, game.getCurrentPlayer());
                    if (moveStatus.isDone()) {
                        copyOfGame.switchTurn();
                        dividedTasks.add(new Min(new Game(copyOfGame), depth - 1,this.evaluator));
                    }
                }
            }
            return dividedTasks;
        }
    }
}

