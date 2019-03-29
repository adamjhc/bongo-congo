package com.knightlore.networking.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Singular list game response object holds connection information and player usernames
 *
 * @author Lewis Relph
 */
public class ListGameObject {

  UUID uuid;
  InetAddress ip;
  int port;
  ArrayList<String> usernames;
  String name;

  public ListGameObject(UUID uuid, InetAddress ip, int port, String name) {
    this.uuid = uuid;
    this.ip = ip;
    this.port = port;
    this.name = name;
    this.usernames = new ArrayList<>();
  }

  /**
   * Add singular user to our list of usernames
   *
   * @param username
   */
  public void addUser(String username) {
    this.usernames.add(username);
  }

  /**
   * Getter for property 'uuid'.
   *
   * @return Value for property 'uuid'.
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * Getter for property 'ip'.
   *
   * @return Value for property 'ip'.
   */
  public InetAddress getIp() {
    return ip;
  }

  /**
   * Getter for property 'port'.
   *
   * @return Value for property 'port'.
   */
  public int getPort() {
    return port;
  }

  /**
   * Getter for property 'usernames'.
   *
   * @return Value for property 'usernames'.
   */
  public ArrayList<String> getUsernames() {
    return usernames;
  }

  /**
   * Setter for property 'usernames'.
   *
   * @param usernames Value to set for property 'usernames'.
   */
  public void setUsernames(ArrayList<String> usernames) {
    this.usernames = usernames;
  }

  /**
   * Getter for property 'name'.
   *
   * @return Value for property 'name'.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for property 'name'.
   *
   * @param name Value to set for property 'name'.
   */
  public void setName(String name) {
    this.name = name;
  }
}
