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
	
	String status;
	
	AudioInputStream source;
	static String filePath;
	
	
	
	public MusicPlayer(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.filePath = file;
		this.source = AudioSystem.getAudioInputStream(new File (filePath).getAbsoluteFile());
		
		this.status = "stopped";
		
		this.clip = AudioSystem.getClip();
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (this.status.equals("stopped")) {
			this.resetStream();
			this.clip.stop();
		}
		
		clip.start();
		this.status = "playing";
	}
	
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.clip.stop();
		this.clip.close();
		this.status = "stopped";
	}
	
	private void resetStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.source = AudioSystem.getAudioInputStream(new File (filePath).getAbsoluteFile());
		this.clip.open(this.source);
		this.clip.stop();
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	
	public boolean isPlaying() {
		if (this.status.equals("playing")) {
			return true;
		} else {
			return false;
		}
	}
}
