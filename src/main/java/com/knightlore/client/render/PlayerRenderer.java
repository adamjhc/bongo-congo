package com.knightlore.client.render;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.PlayerSet;
import com.knightlore.game.entity.Player;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PlayerRenderer {

  /** Player set used in the world */
  private PlayerSet playerSet;

  /**
   * Initialise the Player renderer
   *
   * @param playerSet The player set to use to render
   */
  public PlayerRenderer(PlayerSet playerSet) {
    this.playerSet = playerSet;
  }

  /**
   * Renders the player in the world
   *
   * @param player Game Player object to render
   * @param shaderProgram Shader program to use
   * @param camera Camera projection
   */
  public void render(Player player, ShaderProgram shaderProgram, Matrix4f camera) {
    Vector3f position = player.getPosition();

    playerSet
        .getPlayer(player.getId())
        .render(
            player.getPlayerState(),
            player.getDirection(),
            position.x + position.z,
            position.y + position.z,
            shaderProgram,
            camera);
  }
}
