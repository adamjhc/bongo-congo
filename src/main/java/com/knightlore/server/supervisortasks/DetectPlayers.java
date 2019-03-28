package com.knightlore.server.supervisortasks;

import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.database.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

public class DetectPlayers implements SupervisorTask{

    public ArrayList<String> detectedPlayers;

    public DetectPlayers(){
        detectedPlayers = new ArrayList<>();
    }

    public void run(GameServer server){
        if(server.inLobby()){
            for(ClientHandler handler : server.registeredClients()){
                if(handler.sessionKey.isPresent()){
                    if( !detectedPlayers.contains(handler.sessionKey.get())){
                        // Notify db
                        // Get session id
                        SessionToken token = new SessionToken();
                        token.where(new Condition("token", "=", handler.sessionKey.get()));
                        Optional<Model> sessionToken = token.first();

                        Game game = new Game();
                        game.where(new Condition("uuid", "=", handler.server().getUUID().toString()));
                        Optional<Model> gameModel = game.first();


                        if(sessionToken.isPresent() && gameModel.isPresent()){
                            GamePlayer current = new GamePlayer();
                            current.setAttribute("session_id", sessionToken.get().getAttribute("id"));
                            current.setAttribute("score", 0);
                            current.setAttribute("game_id", gameModel.get().getAttribute("id"));


                            Calendar calendar = Calendar.getInstance();
                            java.util.Date now = calendar.getTime();
                            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());


                            current.setAttribute("created_at", currentTimestamp);
                            current.setAttribute("updated_at", currentTimestamp);
                            current.save();
                        }

                        // Update
                        detectedPlayers.add(handler.sessionKey.get());
                    }
                }
            }
        }
    }
}
