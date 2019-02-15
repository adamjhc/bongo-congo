package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import com.knightlore.client.util.BufferUtils;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ShaderProgram {

  /** OpenGL id of the shader program */
  private final int id;

  /** Reference to the vertex shader */
  private Shader vertexShader;

  /** Reference to the fragment shader */
  private Shader fragmentShader;

  /** Cache to save calling glGetUniformLocation every time */
  private Map<String, Integer> locationCache = new HashMap<>();

  /**
   * Initialise the shader program
   *
   * @param shaderFileName File name of the vertex and fragment shaders
   */
  public ShaderProgram(String shaderFileName) {
    id = glCreateProgram();

    vertexShader = new Shader(GL_VERTEX_SHADER, shaderFileName);
    fragmentShader = new Shader(GL_FRAGMENT_SHADER, shaderFileName);

    attachShader(vertexShader.getId());
    attachShader(fragmentShader.getId());

    bindAttribLocation(0, "vertices");
    bindAttribLocation(1, "textures");

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

  /** Sets the shader program as the current program */
  public void bind() {
    glUseProgram(id);
  }

  /**
   * Set uniform variable inside the shader
   *
   * @param name Name of the variable
   * @param value Value to set to
   */
  public void setUniform(String name, int value) {
    glUniform1i(getUniformLocation(name), value);
  }

  /**
   * Set uniform variable inside the shader
   *
   * @param name Name of the variable
   * @param value Value to set to
   */
  public void setUniform(String name, Vector3f value) {
    glUniform3fv(getUniformLocation(name), BufferUtils.createBuffer(value));
  }

  /**
   * Set uniform variable inside the shader
   *
   * @param name Name of the variable
   * @param value Value to set to
   */
  public void setUniform(String name, Vector4f value) {
    glUniform4fv(getUniformLocation(name), BufferUtils.createBuffer(value));
  }

  /**
   * Set uniform variable inside the shader
   *
   * @param name Name of the variable
   * @param value Value to set to
   */
  public void setUniform(String name, Matrix4f value) {
    glUniformMatrix4fv(getUniformLocation(name), false, BufferUtils.createBuffer(value));
  }

  /**
   * Gets the OpenGL uniform variable location
   *
   * @param name Name of the variable
   * @return Location
   */
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

  /**
   * Attaches a shader to the shader program
   *
   * @param shaderId The OpenGL id of the shader
   */
  private void attachShader(int shaderId) {
    glAttachShader(id, shaderId);
  }

  /**
   * Bind an attribute index to a attribute variable
   *
   * @param index Attribute index to bind
   * @param name Variable name to bind to
   */
  private void bindAttribLocation(int index, String name) {
    glBindAttribLocation(id, index, name);
  }

  /** Link the shader program */
  private void link() {
    glLinkProgram(id);

    if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetProgramInfoLog(id));
    }
  }

  /** Validates the shader program */
  private void validate() {
    glValidateProgram(id);

    if (glGetProgrami(id, GL_VALIDATE_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetProgramInfoLog(id));
    }
  }

  /**
   * Clean up memory
   *
   * @throws Throwable Exception
   */
  protected void finalize() throws Throwable {
    glDetachShader(id, vertexShader.getId());
    glDetachShader(id, fragmentShader.getId());
    vertexShader.delete();
    fragmentShader.delete();
    glDeleteProgram(id);
    super.finalize();
  }
}