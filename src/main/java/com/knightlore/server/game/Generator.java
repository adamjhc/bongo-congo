//package com.knightlore.server.game;
//
//import com.knightlore.game.Game;
//import com.knightlore.game.Level;
//import com.knightlore.game.entity.Enemy;
//import com.knightlore.game.map.LevelMap;
//import org.joml.Vector3f;
//
//import java.util.UUID;
//
//public class Generator {
//
//    /**
//     * Build default game model
//     * @return
//     */
//    public static Game makeDefault(UUID gameUUID){
//        // Base game
//        Game game = new Game(gameUUID.toString());
//
//        // Build levels
//        Level levelOne = new Level();
//        LevelMap levelOneMap = new LevelMap(10, 10, 10);
//
//        // Add enemies
//        Enemy enemy = new Enemy();
//        enemy.setPosition(new Vector3f(5, 5, 0));
//
//        levelOne.addEnemy(enemy);
//        levelOne.setLevelMap(levelOneMap);
//
//        // Finally build game
//        game.addLevel(levelOne);
//
//        return game;
//    }
//}
