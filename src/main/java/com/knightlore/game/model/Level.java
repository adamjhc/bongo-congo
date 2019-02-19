package com.knightlore.game.model;

import com.knightlore.game.model.entity.Enemies;
import com.knightlore.game.model.entity.Player;
import com.knightlore.game.model.map.Map;

import java.util.ArrayList;
import java.util.Date;

public class Level {

    Map map;

    ArrayList<Player> players;
    ArrayList<Enemies> enemies;

    Date startedAt;
    int duration;

    public Level(){
        this.enemies = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public void addEnemy(Enemies enemy){
        this.enemies.add(enemy);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}