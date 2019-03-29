package com.knightlore.game.server;

import com.knightlore.networking.game.PositionUpdate;
import com.knightlore.networking.game.PositionUpdateChunk;

/**
 * Queue for storing position update before they are sent out
 *
 * @author Lewis Relph
 */
public class PositionUpdateQueue {

  public static PositionUpdateQueue instance = new PositionUpdateQueue();

  PositionUpdateChunk chunk;

  public PositionUpdateQueue() {
    this.chunk = new PositionUpdateChunk();
  }

  public void add(PositionUpdate u) {
    this.chunk.add(u);
  }

  public PositionUpdateChunk getQueue() {
    return this.chunk;
  }

  public void clear() {
    this.chunk.clear();
  }
}
