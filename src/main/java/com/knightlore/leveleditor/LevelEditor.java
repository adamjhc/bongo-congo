package com.knightlore.leveleditor;

import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileSet;

public class LevelEditor {
	
	private Level level;
	private LevelEditorState state;
	
	public LevelEditor(int width, int length, int height) {
		this.level = new Level(initialiseMap(width, length, height));
		this.state = LevelEditorState.EDITING;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public LevelEditorState getState() {
		return this.state;
	}
	
	public void setState(LevelEditorState state) {
		this.state = state;
	}
	
	private LevelMap initialiseMap(int width, int length, int height) {
		int[][][] emptyMap = new int[width][length][height];
		for (int i = 0; i < emptyMap.length; i++) {
			for (int j = 0; j < emptyMap[i].length; j++) {
				for (int k = 0; k < emptyMap[j].length; k++) {
					if (i == 0) {
						emptyMap[i][j][k] = 1;
					} else {
						emptyMap[i][j][k] = 0;
					}
				}
			}
		}
		return (new LevelMap(emptyMap, (new TileSet())));
	}

}
