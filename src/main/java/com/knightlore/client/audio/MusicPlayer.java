package com.knightlore.client.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class MusicPlayer {

	Clip clip;
	AudioInputStream source;
	String filePath;
	boolean loops;
	FloatControl gainControl;
	
	
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
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-80.0f);
		this.clip.stop();
		if (this.loops) {
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	
	public void incVolume() {
		System.out.println(gainControl.getMaximum());
		System.out.println(gainControl.getMinimum());
		float prev = gainControl.getValue();
		gainControl.setValue(Math.min(prev + 0.86f, gainControl.getMaximum()));
	}
	
	public void decVolume() {
		float prev = gainControl.getValue();
		gainControl.setValue(Math.max(prev - 0.86f, gainControl.getMinimum()));
	}
	
	// Gets whether the sound is currently being played or not
	public boolean isPlaying() {
		return this.clip.isActive();
	}
}
