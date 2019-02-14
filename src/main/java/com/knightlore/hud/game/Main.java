package com.knightlore.hud.game;

import com.knightlore.hud.engine.GameEngine;
import com.knightlore.hud.engine.IGameLogic;
 
public class Main {
 
    public static void main(String[] args) {
        try {
            IGameLogic gameLogic = new GameLogic();			  //600, 480
            GameEngine gameEng = new GameEngine("Bongo Congo", 1280, 720, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}