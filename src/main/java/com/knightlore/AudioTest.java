package com.knightlore;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class AudioTest {

	Long currentPoint;
	Clip clip;
	
	String status;
	
	AudioInputStream source;
	static String filePath;
	
	
	
	public AudioTest() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.source = AudioSystem.getAudioInputStream(new File (filePath).getAbsoluteFile());
		
		this.status = "stopped";
		
		this.clip = AudioSystem.getClip();
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void main(String[] args) {
		try {
			filePath = "src/main/java/com/knightlore/ingame-02-cavern.wav";
			
			AudioTest player = new AudioTest();
			Scanner scanner = new Scanner(System.in);
			
			player.play();
			String input;
			while (!(input = scanner.next()).equals("q")) {
				player.chooseAction(input);
			}
			
		} catch (Exception ex) {
			System.out.println("Error playing music");
			ex.printStackTrace();
		}
	}
	
	
	private void chooseAction(String c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (c.equals("p")) {
			if (this.status.equals("playing")) {
				this.pause();
			} else {
				this.play();
			}
		} else if (c.equals("s")) {
			this.stop();
		} else {
			return;
		}
	}
	
	
	
	private void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (this.status.equals("paused")) {
			this.clip.close();
			this.resetStream();
			this.clip.setMicrosecondPosition(this.currentPoint);
		}
		
		if (this.status.equals("stopped")) {
			this.resetStream();
			this.clip.stop();
		}
		
		clip.start();
		this.status = "playing";
	}
	
	private void pause() {
		this.currentPoint = this.clip.getMicrosecondPosition();
		this.clip.stop();
		this.status = "paused";
	}
	
	private void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.currentPoint = 0L;
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
}
