package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import com.knightlore.client.util.FileUtils;

public class Shader {

  private final int id;

  public Shader(int type, String shaderPath) {
    String shader = FileUtils.readFileAsString(shaderPath);
    id = createShader(type);
    source(shader);
    compile();
  }

  public int getId() {
    return id;
  }

  public void delete() {
    glDeleteShader(id);
  }

  private int createShader(int type) {
    return glCreateShader(type);
  }

  private void source(String shader) {
    glShaderSource(id, shader);
  }

  private void compile() {
    glCompileShader(id);

    if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetShaderInfoLog(id));
    }
  }
}
