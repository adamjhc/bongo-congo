package com.knightlore.client.render;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.Mesh;
import com.knightlore.client.gui.engine.graphics.Transformation;
import com.knightlore.client.render.opengl.ShaderProgram;
import org.joml.Matrix4f;

public class GuiRenderer extends Renderer {

  private ShaderProgram shaderProgram;
  private Transformation transformation;

  public GuiRenderer(Window window) {
    super(window);

    shaderProgram = new ShaderProgram("gui");
    transformation = new Transformation();
  }

  public void render(IGui gui) {
    clearBuffers();

    renderGui(gui);

    swapBuffers();
  }

  public void renderGui(IGui gui) {
    shaderProgram.bind();

    Matrix4f ortho =
        transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
    for (GuiObject guiObject : gui.getGuiObjects()) {
      if (guiObject.getRender()) {
        Mesh mesh = guiObject.getMesh();

        Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(guiObject, ortho);
        shaderProgram.setUniform("projModelMatrix", projModelMatrix);
        shaderProgram.setUniform("colour", guiObject.getMesh().getMaterial().getColour());
        shaderProgram.setUniform(
            "hasTexture", guiObject.getMesh().getMaterial().isTextured() ? 1 : 0);

        mesh.render();
      }
    }
  }

  @Override
  protected void cleanup() {
    shaderProgram.cleanup();
  }
}