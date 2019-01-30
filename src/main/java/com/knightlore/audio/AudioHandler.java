package com.knightlore.audio;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHandler {
	
	public enum AudioName {
		MUSIC_MENU,
		MUSIC_GAME,
		MUSIC_ENDGAME,
		SOUND_MENUSELECT,
		SOUND_HIT,
		SOUND_DEATH,
		SOUND_ROLL,
		JINGLE_VICTORY,
		JINGLE_GAMEOVER
	}
	
	MusicPlayer menuMusic;
	MusicPlayer gameMusic;
	MusicPlayer endgameMusic;
	MusicPlayer menuSelectSound;
	MusicPlayer hitSound;
	MusicPlayer deathSound;
	MusicPlayer rollSound;
	MusicPlayer victoryJingle;
	MusicPlayer gameOverJingle;
	
	MusicPlayer[] audioFiles;
	
	public AudioHandler() {
		String audioPath = "src/main/resources/audio/";
		/* this.menuMusic = 		new MusicPlayer(audioPath + "MUSIC_MENU.wav", true);
		   this.gameMusic = 		new MusicPlayer(audioPath + "MUSIC_GAME.wav", true);
		   this.endgameMusic = 		new MusicPlayer(audioPath + "MUSIC_ENDGAME.wav", true);
		   this.menuSelectSound = 	new MusicPlayer(audioPath + "SOUND_MENUSELECT.wav", false);
		   this.hitSound = 			new MusicPlayer(audioPath + "SOUND_HIT.wav", false);
		   this.deathSound = 		new MusicPlayer(audioPath + "SOUND_DEATH.wav", false);
		   this.rollSound = 		new MusicPlayer(audioPath + "SOUND_ROLL.wav", false);
		   this.victoryJingle = 	new MusicPlayer(audioPath + "JINGLE_VICTORY.wav", false);
		   this.gameOverJingle = 	new MusicPlayer(audioPath + "JINGLE_GAMEOVER.wav", false);
		 */
		
		this.audioFiles = new MusicPlayer[] {menuMusic, gameMusic, endgameMusic,
											 menuSelectSound, hitSound, deathSound,
											 rollSound, victoryJingle, gameOverJingle};
	}

	// param1: the audio file to be played
	public void play(AudioName file) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		this.audioFiles[file.ordinal()].play();
	}
	
	// Closes any audio files that have finished playing so that they can be played again
	public void closeInactiveClips() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		for (int i = 0; i < this.audioFiles.length; i++) {
			MusicPlayer currentFile;
			if (!(currentFile = this.audioFiles[i]).isPlaying()) {
				currentFile.stop();
			}
		}
	}
}
