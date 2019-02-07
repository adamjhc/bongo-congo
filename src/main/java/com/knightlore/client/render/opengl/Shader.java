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

  private static final String shaderPathPrefix = "./src/main/resources/shaders/";
  private static final HashMap fileExtensions =
      new HashMap<Integer, String>() {
        {
          put(GL_VERTEX_SHADER, ".vert");
          put(GL_FRAGMENT_SHADER, ".frag");
        }
      };

  private final int id;

  Shader(int type, String shaderFileName) {
    id = createShader(type);

    source(
        FileUtils.readFileAsString(shaderPathPrefix + shaderFileName + fileExtensions.get(type)));

    compile();
  }

  int getId() {
    return id;
  }

  void delete() {
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
