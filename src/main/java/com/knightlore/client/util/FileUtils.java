package com.knightlore.client.util;

import com.knightlore.client.render.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileUtils {

  public static String readFileAsString(String filePath) {
    StringBuilder stringBuilder = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(new java.io.File(filePath)))) {
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append("\n");
      }
    } catch (IOException ex) {
      ex.getMessage();
    }

    return stringBuilder.toString();
  }

  public static Image loadImage(String filePath) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filePath));

      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();
      int[] pixels = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

      return new Image(width, height, pixels);
    } catch (IOException e) {
      throw new IllegalStateException("Texture does not exist");
    }
  }
}
