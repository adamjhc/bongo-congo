package com.knightlore.networking;

public class SessionKeyResponse {

    public String key;
    public Boolean success;
    public String username;

    public SessionKeyResponse(boolean success) {
        this.success = success;
        this.key = "";
    }
    public SessionKeyResponse(boolean success, String key, String username) {
        this.success = success;
        this.key = key;
        this.username = username;
    }
}
