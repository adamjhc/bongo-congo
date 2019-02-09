package com.knightlore.client.render;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.PlayerSet;
import com.knightlore.game.entity.Player;
import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public class PlayerRenderer {

  private PlayerSet playerSet;

  public PlayerRenderer(PlayerSet playerSet) {
    this.playerSet = playerSet;
  }

  public void render(Player player, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    Vector3f position = player.getPosition();

    playerSet
        .getPlayer(player.getId())
        .render(position.x + position.z, position.y + position.z, shaderProgram, world, camera);
  }
}
