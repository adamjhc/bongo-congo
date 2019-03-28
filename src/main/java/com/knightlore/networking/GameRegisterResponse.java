package com.knightlore.networking;

import java.util.UUID;

/**
 * Response to game register request, sends game UUID
 *
 * @author Lewis Relph
 */
public class GameRegisterResponse {

  public UUID uuid;

  public GameRegisterResponse(UUID uuid) {
    this.uuid = uuid;
  }
}
