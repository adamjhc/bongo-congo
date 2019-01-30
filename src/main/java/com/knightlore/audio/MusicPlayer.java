package com.knightlore.audio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class MusicPlayer {

	Clip clip;
	AudioInputStream source;
	String filePath;
	boolean loops;
	
	
	/* param1: path to audio file
	 * param2: whether or not the audio file loops
	 */
	public MusicPlayer(String file, boolean shouldLoop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.filePath = file;
		this.source = AudioSystem.getAudioInputStream(new File (filePath).getAbsoluteFile());
		
		this.loops = shouldLoop;
		
		this.clip = AudioSystem.getClip();
		if (this.loops) {
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	
	// Resets audio stream if necessary and then starts playing the audio file
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (!this.isPlaying()) {
			this.resetStream();
			this.clip.stop();
		}
		
		clip.start();
	}
	
	// Stops and closes the audio file (closing is necessary so that it can be played again)
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.clip.stop();
		this.clip.close();
	}
	
	// Resets the audio stream. Once an audio file is closed it cannot be closed unless the audio stream is reset
	private void resetStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.source = AudioSystem.getAudioInputStream(new File (filePath).getAbsoluteFile());
		this.clip.open(this.source);
		this.clip.stop();
		if (this.loops) {
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	
	// Gets whether the sound is currently being played or not
	public boolean isPlaying() {
		return this.clip.isActive();
	}
}
