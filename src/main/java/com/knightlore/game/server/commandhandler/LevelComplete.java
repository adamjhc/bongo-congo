package com.knightlore.game.server.commandhandler;

import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

public class LevelComplete extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Level complete called");

    Player player = handler.model().getPlayers().get(handler.sessionKey.get());
    player.setPlayerState(PlayerState.FINISHED);

    // Check for all complete
    boolean allFinished = true;
    for(Player current: handler.model().getPlayers().values()){
      if(current.getPlayerState() != PlayerState.FINISHED){
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
      }

      handler.model().incrementLevel();
    }

  }
}
