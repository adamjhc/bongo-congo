package com.knightlore.leveleditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.knightlore.game.map.Map;
import com.knightlore.game.map.TileSet;

public class LevelReader {
	
	private File levelFile;
	private FileReader fileReader;
	private BufferedReader levelReader;
	private TileSet tileset;
	
	public LevelReader(String filePath) throws FileNotFoundException {
		this.levelFile = new File(filePath);
		this.fileReader = new FileReader(levelFile);
		this.levelReader = new BufferedReader(fileReader);
		this.tileset = new TileSet();
	}
	
	public Map getLevel() throws IncorrectMapFormatException {
		String mapString = "";
		try {
			mapString = this.readLevel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] str_layers = mapString.split("\n\n");
		int[][][] map = new int[str_layers.length][][];
		int baseSize = str_layers[0].length();
		for (int i = 0; i < str_layers.length; i++) {
			if (!str_layers[i].matches("(\\d\\s)+\\d*")) {
				throw new IncorrectMapFormatException("Maps must be rows of single digits separated by a single space, each row by a new line and each block by 2 new lines");
			} else if (str_layers[i].length() != baseSize) {
				throw new IncorrectMapFormatException("Each layer must be the same size as the last");
			}
			String[] str_rows = str_layers[i].split("\n");
			int baseLength = str_rows[0].length();
			int[][] rows = new int[str_rows.length][];
			
			for (int j = 0; j < str_rows.length; j++) {
				if (str_rows[j].length() != baseLength) {
					throw new IncorrectMapFormatException("Each row must be the same length as the last");
				}
				String[] str_tiles = str_rows[j].split(" ");
				int[] newRow = new int[str_tiles.length];
				
				for (int k = 0; k < str_tiles.length; k++) {
					newRow[k] = Integer.parseInt(str_tiles[k]);
				}
				rows[j] = newRow;
			}
			map[i] = rows;
		}
		return new Map(map, this.tileset);
	}
	
	private String readLevel() throws IOException {
		String mapString = "";
		while(true) {
			String lineOne = this.levelReader.readLine();
			String lineTwo = this.levelReader.readLine();
			if (lineOne == null) {
				break;
			} else if (lineTwo == null) {
				mapString = mapString + lineOne;
				break;
			} else {
				mapString = mapString + lineOne + "\n" + lineTwo + "\n";
			}
		}
		
		return mapString;
	}

}
