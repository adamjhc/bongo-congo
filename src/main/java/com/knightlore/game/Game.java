package com.knightlore.game;

import java.util.ArrayList;

public class Game {

    String uuid;
    ArrayList<Level> levels;
    Integer currentLevelIndex;
    GameState currentState;

    public Game(String uuid){
        this.uuid = uuid;
        this.currentState = GameState.LOBBY;
        this.levels = new ArrayList<>();
    }

    public Game(String uuid, GameState currentState){
        this.uuid = uuid;
        this.currentState = currentState;
        this.levels = new ArrayList<>();
    }

    public Level getCurrentLevel(){
        return this.levels.get(this.currentLevelIndex);
    }

    public int addLevel(Level level){
        this.levels.add(level);

        return this.levels.size() - 1;
    }

    public GameState getState() {
        return currentState;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public void setLevel(int index){
        this.currentLevelIndex = index;
    }

    public void incrementLevel(){
        this.currentLevelIndex += 1;
    }
}
