package com.knightlore.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;

import java.io.ObjectOutputStream;

public abstract class Command {

    Gson gson = new Gson();

    public abstract void run(ClientHandler clientHandler, Sendable sendable);



}
