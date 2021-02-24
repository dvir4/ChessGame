package Player;


import Game.AllianceColor;
import piece.Tool;

import java.util.Collection;

public class WhitePlayer extends Player{
    public WhitePlayer(Collection<Tool> tools) {
        super(AllianceColor.White,tools);
    }
}
