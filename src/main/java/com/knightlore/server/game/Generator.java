package com.knightlore.server.game;

import com.knightlore.game.Game;
import com.knightlore.game.Level;
import com.knightlore.game.entity.Enemies;
import com.knightlore.game.map.Map;
import com.knightlore.game.math.Vector3f;

import java.util.UUID;

public class Generator {

    /**
     * Build default game model
     * @return
     */
    public static Game makeDefault(UUID gameUUID){
        // Base game
        Game game = new Game(gameUUID.toString());

        // Build levels
        Level levelOne = new Level();
        Map levelOneMap = new Map(10, 10, 10);

        // Add enemies
        Enemies enemy = new Enemies();
        enemy.setPosition(new Vector3f(5, 5, 0));

        levelOne.addEnemy(enemy);
        levelOne.setMap(levelOneMap);

        // Finally build game
        game.addLevel(levelOne);

        return game;
    }
}
