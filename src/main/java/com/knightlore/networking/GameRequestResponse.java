package com.knightlore.networking;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Response to game request, send connection information
 *
 * @author Lewis Relph
 */
public class GameRequestResponse {

  public UUID uuid;
  public InetAddress ip;
  public int port;

  public GameRequestResponse(UUID uuid, InetAddress ip, int port) {
    this.uuid = uuid;
    this.ip = ip;
    this.port = port;
  }
}
