package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GameItem;
import com.knightlore.client.gui.engine.IGui;

public class Menu implements IGui {
	
    private GameItem[] gameItems;
    
    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

}
