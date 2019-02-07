package com.knightlore.client.world;

import com.knightlore.client.render.RenderModel;
import com.knightlore.client.render.Renderable;
import com.knightlore.client.render.Texture;

public class BaseTile extends Renderable {
  // 256 * 512

  public BaseTile(String textureFileName) {
    texture = new Texture(textureFileName);
    float tileHeight = (float) texture.getHeight() / texture.getWidth();

    float[] vertices =
        new float[] {
          -1f, 2 * tileHeight, 0, // TOP LEFT     0
          1f, 2 * tileHeight, 0, // TOP RIGHT    1
          1f, 0, 0, // BOTTOM RIGHT 2
          -1f, 0, 0, // BOTTOM LEFT  3
        };

    float[] textureCoords =
        new float[] {
          0, 0,
          1, 0,
          1, 1,
          0, 1,
        };

    int[] indices =
        new int[] {
          0, 1, 2,
          2, 3, 0
        };

    model = new RenderModel(vertices, textureCoords, indices);
  }
}
