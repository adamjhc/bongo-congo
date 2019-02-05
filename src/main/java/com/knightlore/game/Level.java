package com.knightlore.game;

import com.knightlore.game.entity.Enemies;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;

import java.util.ArrayList;
import java.util.Date;

public class Level {

    Map map;

    ArrayList<Player> players;
    ArrayList<Enemies> enemies;

    Date startedAt;
    int duration;

    public Level(){
    }
}
