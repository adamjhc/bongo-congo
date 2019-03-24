package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.NameLevel;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;

import static org.lwjgl.glfw.GLFW.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joml.Vector4f;

public class NameLevelScreen implements IScreen {
	
	/**
	 * The list of all characters that we respond to the user pressing
	 */
	private static ArrayList<Integer> acceptableCharacters = new ArrayList<Integer>(Arrays.asList(
			new Integer[] { GLFW_KEY_1, GLFW_KEY_2, GLFW_KEY_3, GLFW_KEY_4, GLFW_KEY_5, GLFW_KEY_6, GLFW_KEY_7, GLFW_KEY_8, GLFW_KEY_9, GLFW_KEY_0,
						GLFW_KEY_Q, GLFW_KEY_W, GLFW_KEY_E, GLFW_KEY_R, GLFW_KEY_T, GLFW_KEY_Y, GLFW_KEY_U, GLFW_KEY_I, GLFW_KEY_O, GLFW_KEY_P,
						GLFW_KEY_LEFT_BRACKET, GLFW_KEY_RIGHT_BRACKET,  GLFW_KEY_A, GLFW_KEY_S, GLFW_KEY_D, GLFW_KEY_F, GLFW_KEY_G, GLFW_KEY_H,
						GLFW_KEY_J, GLFW_KEY_K, GLFW_KEY_K, GLFW_KEY_L, GLFW_KEY_SEMICOLON, GLFW_KEY_APOSTROPHE,        GLFW_KEY_Z, GLFW_KEY_X,
						GLFW_KEY_C, GLFW_KEY_V, GLFW_KEY_B, GLFW_KEY_N, GLFW_KEY_M, GLFW_KEY_KP_SUBTRACT, GLFW_KEY_KP_ADD, GLFW_KEY_BACKSPACE,
						GLFW_KEY_SPACE}));
	
	/**
	 * The renderer used to render the menu
	 */
	private GuiRenderer guiRenderer;
	
	/**
	 * The object containing the menu GUI
	 */
	private NameLevel nameLevelUi;
	
	/**
	 * The map for the level being saved
	 */
	private LevelMap level;
	
	public NameLevelScreen(GuiRenderer guiRenderer) {
		this.guiRenderer = guiRenderer;
		this.nameLevelUi = new NameLevel();
	}
	
	/**
	 * Method to set up the screen with the level being saved
	 */
	public void startup(Object...args) {
		level = (LevelMap) args[0];
	}
	
	/**
	 * Method to process user keyboard input and clicking on menu items
	 */
	public void input() {
		int lastKey = Keyboard.getKeyCode();
		if (lastKey != -1 && acceptableCharacters.contains(lastKey)) {
			if (lastKey == GLFW_KEY_BACKSPACE) {
				TextObject levelName = nameLevelUi.getLevelName();
				if (levelName.getText().length() <= 1) {
					levelName.setText("");
				} else {
					levelName.setText(levelName.getText().substring(0, levelName.getText().length()-1));
				}
			} else {
				TextObject levelName = nameLevelUi.getLevelName();
				if (levelName.getText().length() < 30)
					levelName.setText(levelName.getText() + (Character.toString((char) lastKey)));
			}
		}
		
		if (checkPosition(nameLevelUi, nameLevelUi.getSaveAndContinue().getId())) {
			nameLevelUi.getSaveAndContinue().setColour();
			if (Mouse.isLeftButtonPressed()) {
				try {
					save(false, nameLevelUi.getLevelName().getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Client.changeScreen(ClientState.LEVEL_EDITOR, level);
			}
		} else nameLevelUi.getSaveAndContinue().setColour(new Vector4f(1, 1, 0, 1));
		
		if (checkPosition(nameLevelUi, nameLevelUi.getSaveAndQuit().getId())) {
			nameLevelUi.getSaveAndQuit().setColour();
			if (Mouse.isLeftButtonPressed()) {
				try {
					save(false, nameLevelUi.getLevelName().getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Client.changeScreen(ClientState.MAIN_MENU);
			}
		} else nameLevelUi.getSaveAndQuit().setColour(new Vector4f(1, 1, 0, 1));
		
		if (checkPosition(nameLevelUi, nameLevelUi.getCancel().getId())) {
			nameLevelUi.getCancel().setColour();
			if (Mouse.isLeftButtonPressed()) {
				Client.changeScreen(ClientState.LEVEL_EDITOR, level);
			}
		} else nameLevelUi.getCancel().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	/**
	 * Method to render the GUI
	 */
	public void render() {
		nameLevelUi.updateSize();
		guiRenderer.render(nameLevelUi);
	}

	/**
	 * Method to clean up the GUI
	 */
	@Override
	public void cleanUp() {
		nameLevelUi.cleanup();
		
	}
	
	/**
	 * Method to save the level being named to a file
	 * @param levelIsComplete Whether or not the level is okay to use in Singleplayer
	 * @param name	The name of the level
	 * @throws IOException Thrown when the file path can't be found
	 */
	private void save(boolean levelIsComplete, String name) throws IOException {
		  String filePath = "customMaps/";
		  if (levelIsComplete) {
			  filePath = filePath + "playable/";
		  } else {
			  filePath = filePath + "unplayable/";
		  }

		  GsonBuilder builder = new GsonBuilder();
		  Gson gson = builder.create();

		  String jsonString = gson.toJson(level);

		  BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + name + ".umap"));
		  writer.write(jsonString);
		  writer.close();
	  }

}
