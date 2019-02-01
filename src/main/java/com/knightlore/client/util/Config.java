package com.knightlore.client.util;

import java.util.Optional;

public class Config {
    private static ConfigReader env = ConfigReader.handler;

    public static Optional<String> sessionKey() {
        Optional<String> sessionKey = env.getVariable("session_key");

        // Split on comma
        return sessionKey;
    }


}
