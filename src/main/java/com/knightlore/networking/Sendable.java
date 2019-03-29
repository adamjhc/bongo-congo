package com.knightlore.networking;

import java.io.Serializable;
import java.util.UUID;

/**
 * Wrapper class to send other networking elements inside Function determines internal packet
 * routing
 *
 * @author Lewis Relph
 */
public class Sendable implements Serializable {

  public boolean success;
  private String function;
  private String uuid;
  private String data;

  /** Default constructor */
  public Sendable() {
    // Randomize the initial UUID
    this.uuid = UUID.randomUUID().toString();
  }

  /**
   * Getter for property 'uuid'.
   *
   * @return Value for property 'uuid'.
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * Setter for property 'uuid'.
   *
   * @param uuid Value to set for property 'uuid'.
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * Getter for property 'data'.
   *
   * @return Value for property 'data'.
   */
  public String getData() {
    return data;
  }

  /**
   * Setter for property 'data'.
   *
   * @param data Value to set for property 'data'.
   */
  public void setData(String data) {
    this.data = data;
  }

  /**
   * Getter for property 'function'.
   *
   * @return Value for property 'function'.
   */
  public String getFunction() {
    return function;
  }

  /**
   * Setter for property 'function'.
   *
   * @param function Value to set for property 'function'.
   */
  public void setFunction(String function) {
    this.function = function;
  }

  /**
   * Check for uuid set
   *
   * @return uuid is set
   */
  public boolean hasUUID() {
    if (this.uuid.equals("")) {
      return false;
    }

    return true;
  }

  /** Regenerate the UUID randomly */
  public void setUuid() {
    this.uuid = UUID.randomUUID().toString();
  }

  /**
   * Create a new sendable object of the same UUID
   *
   * @return sendable response
   */
  public Sendable makeResponse() {
    Sendable toReturn = new Sendable();
    toReturn.uuid = this.uuid;

    return toReturn;
  }
}
