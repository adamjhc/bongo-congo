package com.knightlore.client.util;

import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.io.Mouse;

public class GuiUtils {

  public static boolean checkPosition(IGui gui, String textObject, String textObjectLower) {
    int objectIndex = -1;
    int objectIndexLower = -1;
    for (int i = 0; i < gui.getTextObjects().length; i++) {
      if (gui.getTextObjects()[i].getId().equals(textObject)) {
        objectIndex = i;
        break;
      }
    }

    if (objectIndex == -1) return false;

    if (!textObjectLower.equals("")) {
      for (int i = 0; i < gui.getTextObjects().length; i++) {
        if (gui.getTextObjects()[i].getId().equals(textObjectLower) && i != objectIndex) {
          objectIndexLower = i;
        }
      }

      if (objectIndexLower == -1) return false;
    } else objectIndexLower = objectIndex;

    return Mouse.getXPos() > gui.getTextObjects()[objectIndex].getPositionX()
        && Mouse.getXPos()
            < gui.getTextObjects()[objectIndex].getPositionX()
                + gui.getTextObjects()[objectIndex].getSize()
        && Mouse.getYPos() > gui.getTextObjects()[objectIndex].getPositionY()
        && Mouse.getYPos()
            < gui.getTextObjects()[objectIndexLower].getPositionY()
                + gui.getTextObjects()[objectIndexLower].getHeight();
  }
}
