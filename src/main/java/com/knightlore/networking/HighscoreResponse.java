package com.knightlore.networking;

import java.util.HashMap;

public class HighscoreResponse {

    public HashMap<String, Integer> scores;


    public HighscoreResponse(){
        this.scores = new HashMap<>();
    }

    public void addScore(String username, int score){
        scores.put(username, score);
    }
}
