package AI;

import Game.Game;
import Test.Pair;
import board.Location;

public interface MoveStrategy {
        Pair<Location,Location> makeMoveAI(Game game);
}
