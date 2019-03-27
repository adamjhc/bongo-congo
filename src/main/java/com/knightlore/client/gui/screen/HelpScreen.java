package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import org.joml.Vector4f;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.HelpMenu;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

public class HelpScreen implements IScreen {
	
	private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;
	
	private GuiRenderer renderer;
	
	private HelpMenu helpMenu;
	
	public HelpScreen(GuiRenderer guiRenderer) {
		renderer = guiRenderer;
		helpMenu = new HelpMenu();
	}
	
	public void render() {
		helpMenu.updateSize();
		renderer.render(helpMenu);
	}
	
	public void input() {
		if (checkPosition(helpMenu, helpMenu.getExit().getId())) {
			helpMenu.getExit().setColour();
			if (Mouse.isLeftButtonPressed()) {
				Audio.play(SELECT);
				Client.changeScreen(ClientState.MAIN_MENU, false);
			}
		} else helpMenu.getExit().setColour(new Vector4f(1, 1, 0, 1));
		
		if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
			Client.changeScreen(ClientState.MAIN_MENU, false);
		}
	}

	@Override
	public void cleanUp() {
		renderer.cleanup();	
	}

}
