package com.knightlore.client.render.opengl;

import com.knightlore.client.util.FileUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Wrapper for OpenGL shaders
 *
 * @author Adam Cox
 */
class Shader {

  /** Path to the shaders directory */
  private static final String SHADER_PATH_PREFIX = "./src/main/resources/shaders/";

  /** File extensions for the two types of shaders */
  private static final Map<Integer, String> FILE_EXTENSIONS;

  static {
    Map<Integer, String> map = new HashMap<>();
    map.put(GL_VERTEX_SHADER, ".vert");
    map.put(GL_FRAGMENT_SHADER, ".frag");
    FILE_EXTENSIONS = Collections.unmodifiableMap(map);
  }

  /** OpenGL id of the shader */
  private final int id;

  /**
   * Initialise the shader
   *
   * @param type Type of the shader, either <code>GL_VERTEX_SHADER</code> or <code>
   *     GL_FRAGMENT_SHADER</code>
   * @param shaderFileName File name of the shader
   * @author Adam Cox
   */
  Shader(int type, String shaderFileName) {
    id = glCreateShader(type);

    source(FileUtils.readShader(SHADER_PATH_PREFIX + shaderFileName + FILE_EXTENSIONS.get(type)));

    compile();
  }

  /**
   * Get the OpenGL id of the shader
   *
   * @return OpenGL id
   * @author Adam Cox
   */
  int getId() {
    return id;
  }

  /**
   * Delete the shader
   *
   * @author Adam Cox
   */
  void delete() {
    glDeleteShader(id);
  }

  /**
   * Set the source of the shader
   *
   * @param shader Shader file read as a string
   * @author Adam Cox
   */
  private void source(String shader) {
    glShaderSource(id, shader);
  }

  /**
   * Compile the shader
   *
   * @author Adam Cox
   */
  private void compile() {
    glCompileShader(id);

    if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new IllegalStateException(glGetShaderInfoLog(id));
    }
  }
}
