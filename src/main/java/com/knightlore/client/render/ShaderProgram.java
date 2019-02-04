package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import com.knightlore.game.math.Vector3f;
import com.knightlore.game.math.Vector4f;
import java.util.HashMap;
import java.util.Map;

class ShaderProgram {

  private final int id;
  private Map<String, Integer> locationCache = new HashMap<>();

  ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
    id = glCreateProgram();

    Shader vertexShader = new Shader(GL_VERTEX_SHADER, vertexShaderPath);
    Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER, fragmentShaderPath);

    attachShader(vertexShader.getId());
    attachShader(fragmentShader.getId());

    link();
    validate();

    vertexShader.delete();
    fragmentShader.delete();
  }

  /**
   * Returns the assigned OpenGL Id of the shader program
   *
   * @return Shader program Id
   */
  int getId() {
    return id;
  }

  void setUniform(String name, int value) {
    glUniform1i(getUniformLocation(name), value);
  }

  void setUniform(String name, Vector3f value) {
    glUniform3fv(getUniformLocation(name), value.toBuffer());
  }

  void setUniform(String name, Vector4f value) {
    glUniform4fv(getUniformLocation(name), value.toBuffer());
  }

  private void attachShader(int shaderId) {
    glAttachShader(id, shaderId);
  }

  private void link() {
    glLinkProgram(id);

    if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetProgramInfoLog(id));
    }
  }

  private void validate() {
    glValidateProgram(id);
  }

  private int getUniformLocation(String name) {
    if (locationCache.containsKey(name)) {
      return locationCache.get(name);
    }

    int location = glGetUniformLocation(id, name);
    if (location == -1) {
      throw new RuntimeException(glGetProgramInfoLog(id));
    }

    locationCache.put(name, location);
    return location;
  }
}
