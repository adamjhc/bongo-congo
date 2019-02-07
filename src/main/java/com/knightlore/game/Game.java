package com.knightlore.game;

import java.util.HashMap;

public class Game {

    String uuid;
    HashMap<String, Level> levels;
    String currentLevel;


    public Level getCurrentLevel(){
        return this.levels.get(this.currentLevel);
    }

}
