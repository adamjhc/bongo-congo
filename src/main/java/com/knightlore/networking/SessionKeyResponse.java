package com.knightlore.networking;

/**
 * Response to session key request, return username and session key
 *
 * @author Lewis Relph
 */
public class SessionKeyResponse {

  public String key;
  public Boolean success;
  public String username;

  /**
   * Default constructor, generally used when success = false
   *
   * @param success
   */
  public SessionKeyResponse(boolean success) {
    this.success = success;
    this.key = "";
  }

  /**
   * Additional constructor when success is generally true
   *
   * @param success
   * @param key
   * @param username
   */
  public SessionKeyResponse(boolean success, String key, String username) {
    this.success = success;
    this.key = key;
    this.username = username;
  }
}
