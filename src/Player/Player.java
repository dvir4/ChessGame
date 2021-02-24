package Player;

import Game.AllianceColor;
import piece.Tool;


import java.util.Collection;
public class Player {
    private final AllianceColor color;
    private final Collection<Tool> tools;
//    private boolean isCheck = false;


    protected Player(AllianceColor color, Collection<Tool> tools) {
        this.color = color;
        this.tools = tools;
    }

    public AllianceColor getColor() {
        return color;
    }

    public  Collection<Tool> getPieces(){
        return this.tools;
    }

//    public boolean isCheck() {
//        return isCheck;
//    }
//
//    public void setCheck(boolean check) {
//        isCheck = check;
//    }
}
