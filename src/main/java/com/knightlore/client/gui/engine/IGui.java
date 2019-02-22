package com.knightlore.client.gui.engine;

public interface IGui {

    GuiObject[] getGuiObjects();

    default void cleanup() {
        GuiObject[] guiObjects = getGuiObjects();
        for (GuiObject guiObject : guiObjects) {
            guiObject.getMesh().cleanUp();
        }
    }
}
