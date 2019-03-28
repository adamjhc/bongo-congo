package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;

import java.util.UUID;

public class PlayerDeath extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    // Update our model to reflect
    handler.model().getPlayers().get(handler.sessionKey.get()).decrementLives();

    // Check for death
    if(handler.model().getPlayers().get(handler.sessionKey.get()).getLives() == 0){
      handler.model().getPlayers().get(handler.sessionKey.get()).setPlayerState(PlayerState.DEAD);

      boolean allFinished = true;
      for(Player current: handler.model().getPlayers().values()) {
        if (!(current.getPlayerState() == PlayerState.FINISHED || current.getPlayerState() == PlayerState.DEAD)) {
          allFinished = false;
          break;
        }
      }

      if(allFinished){
        if(handler.model().lastLevel()){
          // Finish game
          Sendable gameComplete = new Sendable();
          handler.model().setState(GameState.FINISHED);
          gameComplete.setFunction("game_complete");
          handler.server().sendToRegistered(gameComplete);
        }else{
          Sendable levelComplete = new Sendable();
          levelComplete.setFunction("level_complete");
          handler.server().sendToRegistered(levelComplete);

          handler.model().nextLevel();
        }
      }
    }

    // Send out update
    Sendable lifeLost = new Sendable();
    lifeLost.setFunction("player_death");

    com.knightlore.networking.PlayerDeath death = new com.knightlore.networking.PlayerDeath(handler.sessionKey.get());
    Gson gson = new Gson();
    lifeLost.setData(gson.toJson(death));

    handler.server().sendToRegisteredExceptSelf(lifeLost, handler.sessionKey.get());
  }
}
