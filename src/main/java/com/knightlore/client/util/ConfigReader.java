package com.knightlore.client.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class ConfigReader {

    public static ConfigReader handler = new ConfigReader();

    private final String ENV_PATH="system.env";
    private HashMap<String, String> variables = new HashMap<>();

    private ConfigReader() {
        // Read file on construction
        this.readFile();

    }

    /**
     * Read environment file and update local variables to match
     */
    private void readFile() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(ENV_PATH))) {
            while ((line = br.readLine()) != null) {

                if(!line.equals(""))
                    addLineToVariables(line);
            }

        }catch(IOException e){
            System.out.println("FAIL");
        }
    }

    /**
     * Split the provided line into variable, value pairs and assign to local variables
     * @param line
     */
    private void addLineToVariables(String line) {
        String[] split = line.split("=");

        // Append to variables
        this.variables.put(split[0], split[1]);
    }

    /**
     * Retrieve raw variable value from variable list
     * @param key
     * @return
     */
    public Optional<String> getVariable(String key) {
        if(this.variables.containsKey(key)) {
            return Optional.of(this.variables.get(key));
        }

        // Key does not exist
        return Optional.empty();
    }
}
