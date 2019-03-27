package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.GameEnd;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class GameEndScreen implements IScreen {

  GameModel gameModel;

  private GuiRenderer guiRenderer;
  private GameEnd gameEnd;

  public GameEndScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    gameEnd = new GameEnd();
  }

  @Override
  public void startup(Object... args) {
    gameModel = (GameModel) args[0];
    setWinner();
    listFinishingPositions();
  }

  @Override
  public void input() {
    if (checkPosition(gameEnd, gameEnd.getExit().getId())) {
      gameEnd.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(Audio.AudioName.SOUND_MENUSELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else gameEnd.getExit().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  private void listFinishingPositions() {
    Map<String, Player> players = gameModel.getPlayers();
    ArrayList<Player> playersList = new ArrayList<>(players.values());

    playersList.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

    gameEnd.displayScores(playersList);
  }

  private void setWinner() {
    boolean draw = false;
    int highestScore = -1;
    Player highestScorePlayer = null;
    Map<String, Player> players = gameModel.getPlayers();
    for (Player player : players.values()) {
      if (player.getScore() > highestScore) {
        highestScorePlayer = player;
        highestScore = player.getScore();
        draw = false;
      } else if (player.getScore() == highestScore) {
        draw = true;
      }
    }
    if (draw) {
      gameEnd.getWinner().setText("Draw");
    } else {
      gameEnd.getWinner().setText("Winner: " + highestScorePlayer.getId());
    }
  }

  @Override
  public void render() {
    gameEnd.updateSize();

    guiRenderer.render(gameEnd);
  }

  @Override
  public void cleanUp() {
    gameEnd.cleanup();
  }
}
