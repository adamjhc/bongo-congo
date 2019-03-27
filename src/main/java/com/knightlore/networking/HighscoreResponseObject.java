package com.knightlore.networking;

import java.util.HashMap;

public class HighscoreResponseObject {

    public String username;
    public int score;


    public HighscoreResponseObject(String username, int score){
        this.username = username;
        this.score = score;
    }

}
