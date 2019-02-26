package com.knightlore.game;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.entity.Enemies;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Level {

    Map map;

    HashMap<String, Player> players;
    ArrayList<Enemies> enemies;

    Date startedAt;
    int duration;

    public Level() {
        this.enemies = new ArrayList<>();
        this.players = new HashMap<>();
    }

    public Level(Map map, HashMap<String, Player> players) {
        this.map = map;
        this.players = players;
    }

    public void addEnemy(Enemies enemy) {
        this.enemies.add(enemy);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public ArrayList<Enemies> getEnemies() {
        return enemies;
    }

    public Player myPlayer(){
        if(GameConnection.instance == null){
            return this.players.get("1");
        }
        return this.players.get(GameConnection.instance.sessionKey);
    }
}
