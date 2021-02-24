package AI;

import Game.Game;
import Player.Player;
import Game.GameLogic;
import piece.Bishop;
import piece.King;
import piece.Tool;
import Game.AllianceColor;

import java.util.Collection;
public class StandardBoardEvaluator implements BoardEvaluator {
    private static final  int CHECK_BONUS = 45;
    private static final int CHECK_MATE_BONUS = 10000;
    private static final  int CASTLE_BONUS = 25;
    private static final  int TWO_BISHOPS_BONUS  = 30;


    @Override
    public int evaluate(Game game, int depth) {
        return scorePlayer(game, game.getWhitePlayer(), depth) -
                scorePlayer(game, game.getBlackPlayer(), depth);
    }

    private int scorePlayer(Game game, Player player, int depth) {
        return pieceValue(game,player) +
                mobility(game,player) +
                check(game,player) +
                castle(game,player) +
                checkMate(game,player,depth);
    }

    private int check(Game game,Player player) {
        Player opponent = player.getColor().equals(AllianceColor.Black) ? game.getWhitePlayer() : game.getBlackPlayer();
        return GameLogic.isCheck(game.getBoard(),opponent) ? CHECK_BONUS : 0;
    }

    private int checkMate(Game game,Player player,int depth) {
        Player opponent = player.getColor().equals(AllianceColor.Black) ? game.getWhitePlayer() : game.getBlackPlayer();
        return GameLogic.isCheckMate(game.getBoard() ,opponent) ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private int depthBonus(int depth) {
        return depth == 0 ? 0 : 100 * depth;
    }

    private  int pieceValue(Game game,Player player) {
        int pieceValueScore = 0;
        int numBishops = 0;
        Collection<Tool> playerTools = player.getColor().equals(AllianceColor.Black) ?
                game.getBoard().getBlackTools() : game.getBoard().getWhiteTools();
        for (Tool tool : playerTools) {
            if(tool instanceof Bishop) numBishops++;
            pieceValueScore += tool.getToolValue();
        }
        //return pieceValueScore;
        return pieceValueScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private  int mobility(Game game,Player player) {
        int sum = 0;
        Collection<Tool> playerTools = player.getColor().equals(AllianceColor.Black) ?
                game.getBoard().getBlackTools() : game.getBoard().getWhiteTools();
        for(Tool tool : playerTools) sum += tool.getPossibleMoves().size();
        return sum;
    }

    private int castle(Game game,final Player player) {
        King king = player.getColor().equals(AllianceColor.Black) ? game.getBoard().getBlackKingLocation() :
                game.getBoard().getWhiteKingLocation();
        return king.isCastled() ? CASTLE_BONUS  : 0;
    }
}
