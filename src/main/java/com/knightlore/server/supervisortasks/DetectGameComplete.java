package com.knightlore.server.supervisortasks;

import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;

import java.util.Map;
import java.util.Optional;

/**
 * Supervisor task for detecting when a game is complete & updating database
 *
 * @author Lewis Relph
 */
public class DetectGameComplete implements SupervisorTask {

    public void run(GameServer server){
        // Check if state is finished
        if(server.getModel().getState() == GameState.FINISHED){
            System.out.println("Game complete detected");

            // Get game id
            Game game = new Game();
            game.where(new Condition("uuid", "=", server.getUUID().toString()));
            Optional<Model> gameModel = game.first();

            // Store scores in database
            for(Map.Entry<String, Player> current: server.getModel().getPlayers().entrySet()){
                // Get session id from session token
                SessionToken session = new SessionToken();
                session.where(new Condition("token", "=", current.getKey()));
                Optional<Model> sessionModel = session.first();

                // Retrieve game player associated
                GamePlayer cPlayer = new GamePlayer();
                cPlayer.where(new Condition("session_id", "=", (int) sessionModel.get().getAttribute("id")));
                cPlayer.where(new Condition("game_id", "=", (int) gameModel.get().getAttribute("id")));
                Optional<Model> cPlayerModel = cPlayer.first();

                // Update score and save
                cPlayerModel.get().setAttribute("score", (int) current.getValue().getScore());
                cPlayerModel.get().update();
            }

            // Close server
            server.close();

            // Update repository
            GameRepository.instance.removeServer(server.getUUID());
        }
    }
}
