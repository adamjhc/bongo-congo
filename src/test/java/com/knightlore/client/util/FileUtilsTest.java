package com.knightlore.client.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.knightlore.client.render.opengl.Image;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilsTest {

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void readShaderTest() {
    // arrange
    String expected =
        "#version 120\n\nattribute vec3 vertices;\nattribute vec2 textures;\n\nvarying vec2"
            + " tex_coords;\n\nuniform mat4 projection;\n\nvoid main() {\n  tex_coords ="
            + " textures;\n  gl_Position = projection * vec4(vertices, 1);\n}\n";
    // act
    String actual = FileUtils.readShader("./src/test/resources/shaders/testShader.vert");

    // assert
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void readerShaderDoesNotExistTest() throws IllegalStateException {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage(equalTo("Shader does not exist"));

    FileUtils.readShader("./src/test/resources/shaders/doesNotExist.frag");
  }

  @Test
  public void loadTextureTest() {
    // arrange
    Image expected = new Image(256, 512, new int[0]);

    // act
    Image actual = FileUtils.loadTexture("./src/test/resources/textures/floor.png");

    // assert
    assertThat(actual.getWidth(), is(equalTo(expected.getWidth())));
    assertThat(actual.getHeight(), is(equalTo(expected.getHeight())));
  }

  @Test
  public void loadTextureDoesNotExistTest() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage(equalTo("Texture does not exist"));

    FileUtils.loadTexture("./src/test/resources/textures/doesNotExist.png");
  }
}
