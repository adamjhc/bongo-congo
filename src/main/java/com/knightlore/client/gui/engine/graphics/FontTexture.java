package com.knightlore.client.gui.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates a texture that contains all the characters for a given font
 *
 * @author Joseph
 */
public class FontTexture {

  /** Character image format */
  private static final String IMAGE_FORMAT = "png";
  /** Font used */
  private final Font font;
  /** Character set used */
  private final String charSetName;
  /** Stores character info */
  private final Map<Character, CharInfo> charMap;
  /** Texture of the character image */
  private Texture texture;
  /** Character height */
  private int height;
  /** Width of all characters */
  private int width;

  /**
   * Initialise values and build texture
   *
   * @param font
   * @param charSetName
   * @author Joseph
   */
  public FontTexture(Font font, String charSetName) {
    this.font = font;
    this.charSetName = charSetName;
    charMap = new HashMap<>();

    buildTexture();
  }

  /**
   * Return width
   *
   * @return Width
   * @author Joseph
   */
  public int getWidth() {
    return width;
  }

  /**
   * Return height
   *
   * @return Height
   * @author Joseph
   */
  public int getHeight() {
    return height;
  }

  /**
   * Return texture
   *
   * @return Texture
   * @author Joseph
   */
  public Texture getTexture() {
    return texture;
  }

  /**
   * Returns the character info for a specific character
   *
   * @param c Character
   * @return Character info
   * @author Joseph
   */
  public CharInfo getCharInfo(char c) {
    return charMap.get(c);
  }

  /**
   * Returns all the available characters
   *
   * @param charsetName Character set
   * @return All available characters
   * @author Joseph
   */
  private String getAllAvailableChars(String charsetName) {
    CharsetEncoder ce = Charset.forName(charsetName).newEncoder();
    StringBuilder result = new StringBuilder();
    for (char c = 0; c < Character.MAX_VALUE; c++) {
      if (ce.canEncode(c)) {
        result.append(c);
      }
    }
    return result.toString();
  }

  /**
   * Creates the texture
   *
   * @author Joseph
   */
  private void buildTexture() {
    // Get the font metrics for each character for the selected font by using image
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2D = img.createGraphics();
    g2D.setFont(font);
    FontMetrics fontMetrics = g2D.getFontMetrics();

    String allChars = getAllAvailableChars(charSetName);
    this.width = 0;
    this.height = 0;
    for (char c : allChars.toCharArray()) {
      // Get the size for each character and update global image size
      CharInfo charInfo = new CharInfo(width, fontMetrics.charWidth(c));
      charMap.put(c, charInfo);
      width += charInfo.getWidth();
      height = Math.max(height, fontMetrics.getHeight());
    }
    g2D.dispose();

    // Create the image associated to the charset
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    g2D = img.createGraphics();
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2D.setFont(font);
    fontMetrics = g2D.getFontMetrics();
    g2D.setColor(Color.WHITE);
    g2D.drawString(allChars, 0, fontMetrics.getAscent());
    g2D.dispose();

    // Dump image to a byte buffer

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      ImageIO.write(img, IMAGE_FORMAT, out);
      out.flush();
      InputStream is = new ByteArrayInputStream(out.toByteArray());
      texture = new Texture(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Character information
   *
   * @author Joseph
   */
  public static class CharInfo {

    /** Starting x */
    private final int startX;
    /** Character width */
    private final int width;

    /**
     * Initialise values
     *
     * @param startX Starting x
     * @param width Character width
     * @author Joseph
     */
    public CharInfo(int startX, int width) {
      this.startX = startX;
      this.width = width;
    }

    /**
     * Return the starting x position
     *
     * @return StartX
     * @author Joseph
     */
    public int getStartX() {
      return startX;
    }

    /**
     * Return the width
     *
     * @return Width
     * @author Joseph
     */
    public int getWidth() {
      return width;
    }
  }
}
