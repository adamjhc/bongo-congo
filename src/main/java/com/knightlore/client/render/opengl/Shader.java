package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import com.knightlore.client.util.FileUtils;
import java.util.HashMap;

class Shader {

  /** Path to the shaders directory */
  private static final String shaderPathPrefix = "./src/main/resources/shaders/";

  /** File extensions for the two types of shaders */
  private static final HashMap fileExtensions =
      new HashMap<Integer, String>() {
        {
          put(GL_VERTEX_SHADER, ".vert");
          put(GL_FRAGMENT_SHADER, ".frag");
        }
      };

  /** OpenGL id of the shader */
  private final int id;

  /**
   * Initialise the shader
   *
   * @param type Type of the shader, either <code>GL_VERTEX_SHADER</code> or <code>
   *     GL_FRAGMENT_SHADER</code>
   * @param shaderFileName File name of the shader
   */
  Shader(int type, String shaderFileName) {
    id = glCreateShader(type);

    source(
        FileUtils.readFileAsString(shaderPathPrefix + shaderFileName + fileExtensions.get(type)));

    compile();
  }

  /**
   * Get the OpenGL id of the shader
   *
   * @return OpenGL id
   */
  int getId() {
    return id;
  }

  /** Delete the shader */
  void delete() {
    glDeleteShader(id);
  }

  /**
   * Set the source of the shader
   *
   * @param shader Shader file read as a string
   */
  private void source(String shader) {
    glShaderSource(id, shader);
  }

  /** Compile the shader */
  private void compile() {
    glCompileShader(id);

    if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException(glGetShaderInfoLog(id));
    }
  }
}
