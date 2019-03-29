package com.knightlore.client.util;

import com.knightlore.client.render.opengl.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileUtils {

  private FileUtils() {}

  public static String readShader(String filePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(new java.io.File(filePath)))) {
      StringBuilder stringBuilder = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append("\n");
      }
      return stringBuilder.toString();
    } catch (IOException ex) {
      throw new IllegalStateException("Shader does not exist");
    }
  }

  public static Image loadTexture(String filePath) {
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
