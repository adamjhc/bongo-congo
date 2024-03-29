package com.knightlore.util;

import java.util.Optional;

/** Global configuration reader Acts as a facade for ConfigReader.java */
public class Config {
  /** Static instance */
  private static ConfigReader env = ConfigReader.handler;

  public static Optional<String> sessionKey() {
    Optional<String> sessionKey = env.getVariable("session_key");

    // Split on comma
    return sessionKey;
  }

  /**
   * Get API Key from configuration file
   *
   * @return
   */
  public static Optional<String> apiKey() {
    Optional<String> apiKey = env.getVariable("api_key");

    // Split on comma
    return apiKey;
  }

  /**
   * Get Auth Server IP from configuration file
   *
   * @return
   */
  public static Optional<String> authServerIp() {
    Optional<String> authServerIp = env.getVariable("auth_server_ip");

    // Split on comma
    return authServerIp;
  }

  /**
   * Get Auth Server Port Key from configuration file
   *
   * @return
   */
  public static Optional<Integer> authServerPort() {
    Optional<String> string = env.getVariable("auth_server_port");

    if (string.isPresent()) {
      return Optional.of(Integer.valueOf(string.get()));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Get Database Host Key from configuration file
   *
   * @return
   */
  public static Optional<String> databaseHost() {
    Optional<String> string = env.getVariable("database_host");

    if (string.isPresent()) {
      return Optional.of(string.get());
    } else {
      return Optional.empty();
    }
  }

  /**
   * Get Database User from configuration file
   *
   * @return
   */
  public static Optional<String> databaseUser() {
    Optional<String> string = env.getVariable("database_user");

    if (string.isPresent()) {
      return Optional.of(string.get());
    } else {
      return Optional.empty();
    }
  }

  /**
   * Get Database Password from configuration file
   *
   * @return
   */
  public static Optional<String> databasePassword() {
    Optional<String> string = env.getVariable("database_password");

    if (string.isPresent()) {
      return Optional.of(string.get());
    } else {
      return Optional.empty();
    }
  }
  /**
   * Get Database Name from configuration file
   *
   * @return
   */
  public static Optional<String> databaseName() {
    Optional<String> string = env.getVariable("database_name");

    if (string.isPresent()) {
      return Optional.of(string.get());
    } else {
      return Optional.empty();
    }
  }
}
