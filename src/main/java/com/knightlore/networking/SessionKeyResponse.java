package com.knightlore.networking;

public class SessionKeyResponse {

    public String key;
    public Boolean success;

    public SessionKeyResponse(boolean success) {
        this.success = success;
        this.key = "";
    }
    public SessionKeyResponse(boolean success, String key) {
        this.success = success;
        this.key = key;
    }
}
