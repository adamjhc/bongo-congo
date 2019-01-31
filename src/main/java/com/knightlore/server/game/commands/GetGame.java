package com.knightlore.server.game.commands;

import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.commandhandler.Command;

import java.io.ObjectOutputStream;

public class GetGame extends Command {

    public void run(ClientHandler handler, Sendable sendable){
        // Print
        System.out.println("GAME REQUEST");

        // Write new com.knightlore.server.game object

    }
}
