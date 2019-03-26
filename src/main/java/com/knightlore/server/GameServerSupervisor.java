package com.knightlore.server;

import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.commandhandler.ListGames;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GameServerSupervisor extends Thread{

    public GameServer server;
    final static Logger logger = Logger.getLogger(GameServerSupervisor.class);

    public GameServerSupervisor(GameServer server){
        detectedPlayers = new ArrayList<>();
        this.server = server;
    }
    public ArrayList<String> detectedPlayers;


    public void run(){
        while(true){
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
                                logger.info("Adding new player to database");

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
            } else if(server.getModel().getState() == GameState.FINISHED){
                logger.info("Game complete detected, detaching from server");

                // Get game id
                Game game = new Game();
                game.where(new Condition("uuid", "=", server.getUUID().toString()));
                Optional<Model> gameModel = game.first();

                // Store scores
                for(Map.Entry<String, Player> current: server.getModel().getPlayers().entrySet()){
                    SessionToken session = new SessionToken();
                    session.where(new Condition("token", "=", current.getKey()));
                    Optional<Model> sessionModel = session.first();

                    GamePlayer cPlayer = new GamePlayer();
                    cPlayer.where(new Condition("session_id", "=", (int) sessionModel.get().getAttribute("id")));
                    cPlayer.where(new Condition("game_id", "=", (int) gameModel.get().getAttribute("id")));
                    Optional<Model> cPlayerModel = cPlayer.first();

                    cPlayerModel.get().setAttribute("score", (int) current.getValue().getScore());
                    cPlayerModel.get().update();
                }

                // Close server
                server.close();

                // Update repository
                GameRepository.instance.removeServer(server.getUUID());
                break;
            }


            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                //
            }
        }
    }
}
