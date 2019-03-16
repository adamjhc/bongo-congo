package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.joml.Vector4f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.LoadLevelMenu;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.Colour;

public class LoadLevelScreen implements IScreen {
	
	private static final String finishedFilePath = "customMaps/playable";
	private static final String unfinishedFilePath = "customMaps/unplayable";
	
	private String currentLevelName;
	
	private GuiRenderer guiRenderer;
	private LoadLevelMenu loadLevelMenu;
	
	public LoadLevelScreen(GuiRenderer guiRenderer) {
		this.guiRenderer = guiRenderer;
		loadLevelMenu = new LoadLevelMenu();
		currentLevelName = "";
	}
	
	public void startup(Object...args) {
		File[] fLevels = (new File(finishedFilePath)).listFiles();
		File[] uLevels = (new File(unfinishedFilePath)).listFiles();
		
		int fCount = fLevels.length;
		TextObject[] allLevels = new TextObject[fCount + uLevels.length];
		for (int i = 0; i < fCount; i++) {
			String fileName = fLevels[i].getName();
			if (fileName.endsWith(".fmap")) {
				allLevels[i] = new TextObject(fileName.substring(0, fileName.length()-5), IGui.SMALL);
				allLevels[i].setId(fileName);
				allLevels[i].setColour(Colour.YELLOW);
			}
		}
		for (int i = 0; i < uLevels.length; i++) {
			String fileName = uLevels[i].getName();
			if (fileName.endsWith(".umap")) {
				allLevels[i + fCount] = new TextObject(fileName.substring(0, fileName.length()-5), IGui.SMALL);
				allLevels[i + fCount].setId(fileName);
				allLevels[i + fCount].setColour(Colour.YELLOW);
			}
		}
		
		loadLevelMenu.setLevels(allLevels);
	}
	
	public void input() {
		if (checkPosition(loadLevelMenu, loadLevelMenu.getLoad().getId())) {
			loadLevelMenu.getLoad().setColour();
			if (Mouse.isLeftButtonPressed() && !currentLevelName.equals("")) {
				if (currentLevelName.contains(".fmap")) {
					Client.changeScreen(ClientState.LEVEL_EDITOR, getMap(finishedFilePath+"/"+currentLevelName));
				} else {
					Client.changeScreen(ClientState.LEVEL_EDITOR, getMap(unfinishedFilePath+"/"+currentLevelName));
				}
			}
		} else loadLevelMenu.getLoad().setColour(Colour.YELLOW);
		
		if (checkPosition(loadLevelMenu, loadLevelMenu.getBack().getId())) {
			loadLevelMenu.getBack().setColour();
			if (Mouse.isLeftButtonPressed()) {
				Client.changeScreen(ClientState.PRE_EDITOR);
			}
		} else loadLevelMenu.getBack().setColour(Colour.YELLOW);
		
		if (checkPosition(loadLevelMenu, loadLevelMenu.getNextPage().getId())) {
			loadLevelMenu.getNextPage().setColour();
			if (Mouse.isLeftButtonPressed()) {
				loadLevelMenu.incPage();
			}
		} else loadLevelMenu.getNextPage().setColour(Colour.YELLOW);
		
		if (checkPosition(loadLevelMenu, loadLevelMenu.getLastPage().getId())) {
			loadLevelMenu.getLastPage().setColour();
			if (Mouse.isLeftButtonPressed()) {
				loadLevelMenu.decPage();
			}
		} else loadLevelMenu.getLastPage().setColour(Colour.YELLOW);
		
		for (int i = 0; i < loadLevelMenu.numOnScreenLevels(); i++) {
			if (checkPosition(loadLevelMenu, loadLevelMenu.getLevel(i).getId())) {
				if (!currentLevelName.equals(loadLevelMenu.getLevel(i).getId()))
					loadLevelMenu.getLevel(i).setColour();
				if (Mouse.isLeftButtonPressed()) {
					loadLevelMenu.getLevel(i).setColour(Colour.GREEN);
					currentLevelName = loadLevelMenu.getLevel(i).getId();
				}
			} else {
				if (!currentLevelName.equals(loadLevelMenu.getLevel(i).getId())) {
					loadLevelMenu.getLevel(i).setColour(Colour.YELLOW);
				}
			}
		}
	}
	
	public void render() {
		loadLevelMenu.updateSize();
		guiRenderer.render(loadLevelMenu);
	}

	@Override
	public void cleanUp() {
		loadLevelMenu.cleanup();
		
	}
	
	  private LevelMap getMap(String filePath) {
			File levelFile = new File(filePath);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String jsonString = "";
			try {
			  FileReader fileReader = new FileReader(levelFile);
			  BufferedReader levelReader = new BufferedReader(fileReader);
			  String line = levelReader.readLine();
			  jsonString = jsonString + line;
			  while ((line = levelReader.readLine()) != null) {
				jsonString = jsonString + line;
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}

			return gson.fromJson(jsonString, LevelMap.class);
		  }

}
