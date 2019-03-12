package com.knightlore.client.render;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.graphics.Mesh;
import com.knightlore.client.gui.engine.graphics.Transformation;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.glViewport;

public class GuiRenderer extends Renderer {

  private ShaderProgram shaderProgram;
  private Transformation transformation;

  public GuiRenderer() {
    super();

    shaderProgram = new ShaderProgram("gui");
    transformation = new Transformation();
  }

  public void render(IGui gui) {
    clearBuffers();
    
    if (Window.isResized()) {
    	glViewport(0, 0, Window.getWidth(), Window.getHeight());
    	Window.setResized(false);
    }

    renderGui(gui);

    swapBuffers();
  }

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

  @Override
  protected void cleanup() {
    shaderProgram.cleanup();
  }
}
