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

import com.knightlore.client.render.math.Vector3f;
import com.knightlore.client.render.math.Vector4f;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {

  private final int id;
  private Map<String, Integer> locationCache = new HashMap<>();

  public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
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
  public int getId() {
    return id;
  }

  public void attachShader(int shaderId) {
    glAttachShader(id, shaderId);
  }

  public void link() {
    glLinkProgram(id);

    if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetProgramInfoLog(id));
    }
  }

  public void validate() {
    glValidateProgram(id);
  }

  public int getUniformLocation(String name) {
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

  public void setUniform(String name, int value) {
    glUniform1i(getUniformLocation(name), value);
  }

  public void setUniform(String name, Vector3f value) {
    glUniform3fv(getUniformLocation(name), value.toBuffer());
  }

  public void setUniform(String name, Vector4f value) {
    glUniform4fv(getUniformLocation(name), value.toBuffer());
  }
}
