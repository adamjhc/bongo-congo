package com.knightlore.client.gui.screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.gui.NameLevel;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NameLevelScreen implements IScreen {
	
	private GuiRenderer guiRenderer;
	private NameLevel nameLevelUi;
	private LevelMap level;
	
	public NameLevelScreen(GuiRenderer guiRenderer) {
		this.guiRenderer = guiRenderer;
		this.nameLevelUi = new NameLevel();
	}
	
	public void input() {
		int lastKey = Keyboard.getKeyCode();
		if (lastKey == GLFW_KEY_BACKSPACE) {
			TextObject levelName = nameLevelUi.getLevelName();
			levelName.setText(levelName.getText().substring(0, levelName.getText().length()-2));
		} else {
			TextObject levelName = nameLevelUi.getLevelName();
			levelName.setText(levelName.getText() + (Character.toString((char) lastKey)));
		}
		
		
	}
	
	public void setLevel(LevelMap level) {
		this.level = level;
	}
	
	public void render() {
		nameLevelUi.updateSize();
		guiRenderer.render(nameLevelUi);
	}

	@Override
	public void cleanUp() {
		nameLevelUi.cleanup();
		
	}
	
	private void save(boolean levelIsComplete) throws IOException {
		  String filePath = "customMaps/";
		  if (levelIsComplete) {
			  filePath = filePath + "playable/";
		  } else {
			  filePath = filePath + "unplayable/";
		  }

		  GsonBuilder builder = new GsonBuilder();
		  Gson gson = builder.create();

		  String jsonString = gson.toJson(level);
		  System.out.println(jsonString);

		  BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "customMap.umap"));
		  writer.write(jsonString);
		  writer.close();
	  }

}
