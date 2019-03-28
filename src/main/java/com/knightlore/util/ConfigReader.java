package com.knightlore.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Actual implementation of config reader, all logic stored here Allows client to read fron a
 * system.env file
 */
public class ConfigReader {

  // Static reader instance (prevent re-read of file)
  public static ConfigReader handler = new ConfigReader();

  // File path for configuration
  private final String ENV_PATH = "system.env";

  // Stored variables
  private HashMap<String, String> variables = new HashMap<>();

  private static final Logger logger = Logger.getLogger(ConfigReader.class);

  /** Default constructor Read file on creation */
  private ConfigReader() {
    this.readFile();
  }

  /** Read environment file and update local variables to match */
  private void readFile() {
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(ENV_PATH))) {
      while ((line = br.readLine()) != null) {
        if (!line.equals("")) addLineToVariables(line);
      }

    } catch (IOException e) {
      logger.warn("Error duirng configuration file reading, does system.env exist?");
    }
  }

  /**
   * Split the provided line into variable, value pairs and assign to local variables
   *
   * @param line
   */
  private void addLineToVariables(String line) {
    String[] split = line.split("=");

    // Add to hashmap, name as key
    this.variables.put(split[0], split[1]);
  }

  /**
   * Retrieve raw variable value from variable list
   *
   * @param key
   * @return optional, value if exists as string
   */
  public Optional<String> getVariable(String key) {
    if (this.variables.containsKey(key)) {
      return Optional.of(this.variables.get(key));
    }

    // Key does not exist
    return Optional.empty();
  }
}
