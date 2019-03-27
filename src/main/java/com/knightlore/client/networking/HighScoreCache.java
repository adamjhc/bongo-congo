package com.knightlore.client.networking;

import com.knightlore.networking.HighscoreResponse;
import com.knightlore.networking.ListLevelObject;
import com.knightlore.networking.ListLevelsResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class HighScoreCache {

    public static HighScoreCache instance = new HighScoreCache();

    HashMap<String, Integer> scores;
    public int cache = 0;

    public HighScoreCache(){
        this.scores = new HashMap<>();
    }

    public void setScores(HighscoreResponse response){
        this.scores = response.scores;
        cache ++;
    }

    public HashMap<String, Integer> getSCores(){
        return this.scores;
    }

}
