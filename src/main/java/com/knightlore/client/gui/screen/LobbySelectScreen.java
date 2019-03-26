package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.LobbyMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.networking.ListGameObject;
import java.util.concurrent.TimeUnit;

public class LobbySelectScreen implements IScreen {
	
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  private GuiRenderer guiRenderer;
  private LobbyMenu menu;

  public LobbySelectScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    menu = new LobbyMenu();
  }

  @Override
  public void startup(Object... args) {
    menuRefresh();
  }

  @Override
  public void input() {
    if (checkPosition(menu, menu.getSeparatorTop().getId(), menu.getSeparatorBot().getId())) {
      if (Mouse.scrolledDown()) {
        menu.moveDown();
      }
      if (Mouse.scrolledUp()) {
        menu.moveUp();
      }
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        menu.highlight();
      }
    }

    if (checkPosition(menu, menu.getCreate().getId())) {
      menu.getCreate().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        Client.showLoadingScreen();
        ServerConnection.instance.requestGame();

        while (GameConnection.instance == null) {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            // Shouldn't happen
          }
        }
        // Sent off register request
        GameConnection.instance.register();

        // Wait for register response
        while(GameConnection.instance.uuid == null){
          try{
            TimeUnit.SECONDS.sleep(1);
          }catch(InterruptedException e){

          }
        }

        // Update game list
        ServerConnection.instance.listGames();
        int cache = LobbyCache.instance.cacheBuster;

        while (cache == LobbyCache.instance.cacheBuster) {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            // Shouldn't happen
          }
        }
        ListGameObject test1 = LobbyCache.instance.getGame(GameConnection.instance.uuid);

        LobbyObject test = new LobbyObject("Test", menu.SMALL, test1);
        test.setIsCreator(true);


        Client.changeScreen(ClientState.LOBBY, false, test);
      }
    } else menu.getCreate().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getExit().getId())) {
      menu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
      }
    } else menu.getExit().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getRefresh().getId())) {
      menu.getRefresh().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        menuRefresh();
      }
    } else menu.getRefresh().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getJoin().getId())) {
      menu.getJoin().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        if (menu.getHighlighted() != null) {
          Client.showLoadingScreen();
          com.knightlore.client.networking.backend.Client gameClient = new com.knightlore.client.networking.backend.Client(menu.getHighlighted().getGame().getIp(), menu.getHighlighted().getGame().getPort());
          gameClient.run();

          GameConnection.instance = new GameConnection(gameClient, ServerConnection.instance.getSessionKey().get());
          // Wait
          while(!GameConnection.instance.ready()){
            try{
              TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
              // Shouldn't happen
            }
          }

          GameConnection.instance.register();

          Client.changeScreen(ClientState.LOBBY, false, menu.getHighlighted());
        }
      }
    } else menu.getJoin().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  @Override
  public void render() {
    menu.updateSize();

    guiRenderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }

  public void menuRefresh(){
    int cacheV = LobbyCache.instance.cacheBuster;

    ServerConnection.instance.listGames();
    while(cacheV == LobbyCache.instance.cacheBuster){
      try{
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Waitings");

      }catch(InterruptedException e){
        // Shouldn't happen
      }
    }
    menu.refreshLobbies();
  }
}
