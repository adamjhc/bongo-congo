package com.knightlore.client.leveleditor;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.client.io.Window;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.joml.Vector4f;

public class LevelEditorHud implements IGui {

  private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);

  private static final String CHARSET = "ISO-8859-1";
  
  private final TextObject save;

  private TextObject[] textObjects;
  private GuiObject[] guiObjects;

  public LevelEditorHud() {
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }

    FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);

    this.save = new TextObject("Save", fontTextureLarge);
    this.save.getMesh().getMaterial().setColour(YELLOW);

    this.save.setPosition(Window.getWidth()-save.getSize()*1.1f, 10);

    guiObjects = new GuiObject[] {save};
    textObjects = new TextObject[] {save};
  }
  
  public TextObject getSave() {
	  return save;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }
}
