package com.knightlore.client.audio;

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
		SOUND_CLIMB,
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
	MusicPlayer climbSound;
	MusicPlayer victoryJingle;
	MusicPlayer gameOverJingle;
	
	MusicPlayer[] audioFiles;
	boolean isOn;
	
	public AudioHandler() {
		String audioPath = "src/main/resources/audio/";
		/* try {
		 * 		this.menuMusic = 		new MusicPlayer(audioPath + "MUSIC_MENU.wav", true);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load menu music: " + e);
		 */
		   try {
			   this.gameMusic = 		new MusicPlayer((audioPath + "MUSIC_GAME.wav"), true);
		   } catch (Exception e) {
			   System.err.println("Could not load game music: " + e);
		   }
		/* try {
		 * 		this.endgameMusic = 		new MusicPlayer(audioPath + "MUSIC_ENDGAME.wav", true);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load endgame music: " + e);
		 * }
		 */
		/* try {
		 * 		 this.menuSelectSound = 	new MusicPlayer(audioPath + "SOUND_MENUSELECT.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load menu select sound: " + e);
		 * }
		 */
		/* try {
		 * 		this.hitSound = 			new MusicPlayer(audioPath + "SOUND_HIT.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load hit sound: " + e);
		 * }
		 */
		/* try {
		 * 		this.deathSound = 		new MusicPlayer(audioPath + "SOUND_DEATH.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load death sound: " + e);
		 * }
		 */
		/* try {
		 * 		 this.rollSound = 		new MusicPlayer(audioPath + "SOUND_ROLL.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load roll sound: " + e);
		 * }
		 */
		/* try {
		 * 		this.climbSound =		new MusicPlayer(audioPath + "SOUND_CLIMB.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load climb sound: " + e);
		 * }
		 */
		/* try {
		 * 		this.victoryJingle = 	new MusicPlayer(audioPath + "JINGLE_VICTORY.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load victory jingle: " + e);
		 * }
		 */
		/* try {
		 * 		this.gameOverJingle = 	new MusicPlayer(audioPath + "JINGLE_GAMEOVER.wav", false);
		 * } catch (Exception e) {
		 * 		System.err.println("Could not load game over jingle: " + e);
		 */
		
		
		this.audioFiles = new MusicPlayer[] {menuMusic, gameMusic, endgameMusic,
											 menuSelectSound, hitSound, deathSound,
											 rollSound, victoryJingle, gameOverJingle};
	}

	// param1: the audio file to be played
	public void play(AudioName file) {
		if (this.isOn) {
			try {
				this.audioFiles[file.ordinal()].play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Closes any audio files that have finished playing so that they can be played again
	public void closeInactiveClips() {
		for (int i = 0; i < this.audioFiles.length; i++) {
			MusicPlayer currentFile;
			if (!(currentFile = this.audioFiles[i]).isPlaying()) {
				try {
					currentFile.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void toggle() {
		this.isOn = !this.isOn;
		if (!this.isOn) {
			for (MusicPlayer audio : this.audioFiles) {
				try {
					gameMusic.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				this.play(AudioName.MUSIC_GAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void incVolume() {
		gameMusic.incVolume();
	}
	
	public void decVolume() {
		gameMusic.decVolume();
	}
}
