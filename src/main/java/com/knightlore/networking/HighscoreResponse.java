package com.knightlore.networking;

import java.util.ArrayList;

public class HighscoreResponse {

    public ArrayList<HighscoreResponseObject> scores;


    public HighscoreResponse(){
        this.scores = new ArrayList<>();
    }

    public void addScore(String username, int score){
        scores.add(new HighscoreResponseObject(username, score));
    }
}
