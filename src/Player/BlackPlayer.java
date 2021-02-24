package Player;

import Game.AllianceColor;
import piece.Tool;

import java.util.Collection;

public class BlackPlayer extends Player{
    public BlackPlayer(Collection<Tool> tools) {
        super(AllianceColor.Black,tools);
    }
}
