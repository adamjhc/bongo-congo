package com.knightlore.hud.game;

import com.knightlore.hud.engine.GameEngine;
import com.knightlore.hud.engine.IGameLogic;
 
public class Main {
 
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new GameLogic();
            GameEngine gameEng = new GameEngine("Bongo Congo", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}