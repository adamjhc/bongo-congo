package com.knightlore.util;

import java.util.Optional;

public class Config {
    private static ConfigReader env = ConfigReader.handler;

    public static Optional<String> sessionKey() {
        Optional<String> sessionKey = env.getVariable("session_key");

        // Split on comma
        return sessionKey;
    }

    public static Optional<String> apiKey() {
        Optional<String> apiKey = env.getVariable("api_key");

        // Split on comma
        return apiKey;
    }

    public static Optional<String> authServerIp() {
        Optional<String> authServerIp = env.getVariable("auth_server_ip");

        // Split on comma
        return authServerIp;
    }

    public static Optional<Integer> authServerPort() {
        Optional<String> string =  env.getVariable("auth_server_port");

        if(string.isPresent()){
            return Optional.of(Integer.valueOf(string.get()));
        }else{
            return Optional.empty();
        }
    }


}
