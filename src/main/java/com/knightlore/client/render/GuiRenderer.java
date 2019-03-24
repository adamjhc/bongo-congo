package com.knightlore.client.render;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.graphics.Mesh;
import com.knightlore.client.gui.engine.graphics.Transformation;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import org.joml.Matrix4f;

/**
 * Renders GUI elements
 *
 * @author Joseph Tuffin, Adam Cox
 */
public class GuiRenderer extends Renderer {

  /** Shader program to use in rendering */
  private ShaderProgram shaderProgram;

  /** Transformation used for projection */
  private Transformation transformation;

  /**
   * Initialise GuiRenderer
   *
   * @author Joseph, Tuffin, Adam Cox
   */
  public GuiRenderer() {
    super();

    shaderProgram = new ShaderProgram("gui");
    transformation = new Transformation();
  }

  /**
   * Render only gui
   *
   * @param gui GUI to render
   * @author Joseph Tuffin
   */
  public void render(IGui gui) {
    clearBuffers();

    renderGui(gui);

    Window.swapBuffers();
  }

  /**
   * Render gui
   *
   * @param gui GUI to render
   * @author Joseph Tuffin
   */
  void renderGui(IGui gui) {
    shaderProgram.bind();

    Matrix4f ortho =
        transformation.getOrthoProjectionMatrix(0, Window.getWidth(), Window.getHeight(), 0);
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

  /**
   * Memory cleanup of shader program
   *
   * @author Adam Cox
   */
  @Override
  public void cleanup() {
    shaderProgram.cleanup();
  }
}
