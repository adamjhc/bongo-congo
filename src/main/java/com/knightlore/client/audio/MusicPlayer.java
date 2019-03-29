package com.knightlore.client.audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Individual object for one audio source
 * @author Adam W, Joseph
 *
 */
class MusicPlayer {

  /**
   * The data line used to play the audio
   */
  private Clip clip;
  
  /**
   * The audio stream from the file
   */
  private AudioInputStream source;
  
  /**
   * The file path to the audio file
   */
  private String filePath;
  
  /**
   * Whether or not this audio file should replay once finished
   */
  private boolean loops;
  
  /**
   * The controller for this sound's volume
   */
  private FloatControl gainControl;
  
  /**
   * The previous volume level of this sound
   */
  private float previousVolume = -19.8f;

  /**
   * Constructor for a MusicPlayer
   * @param file The file path to the sound file
   * @param shouldLoop Whether or not the sound file should restart when it finishes
   * @throws UnsupportedAudioFileException When the given audio file isn't of the correct file type
   * @throws IOException When the given audio file can't be found
   * @throws LineUnavailableException When the line hasn't been closed but the audio is trying to play anyway
   * @author Adam W
   */
  MusicPlayer(String file, boolean shouldLoop)
      throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.filePath = file;
    this.source = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

    this.loops = shouldLoop;

    this.clip = AudioSystem.getClip();

    if (this.loops) {
      this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    resetStream();
    stop();
  }

  /**
   * Method to play this sound
   * @throws UnsupportedAudioFileException When this audio file is of the wrong type
   * @throws IOException When this audio file can't be found
   * @throws LineUnavailableException When this audio file can't be played on this line
   * @author Adam W
   */
  void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (!this.isPlaying()) {
      this.resetStream();
      this.clip.stop();
      clip.start();
    } else if (this.isPlaying()) {
    	return;
    }
  }

  /**
   * Method to stop this audio file
   * @author Adam W
   */
  void stop() {
    this.clip.stop();
    this.clip.close();
  }
  
  /**
   * Method to get whether or not this audio file should repeat
   * @return Whether this audio file should loop
   * @author Adam W
   */
  boolean shouldLoop() {
	  return this.loops;
  }

  /**
   * Method to increase of the volume of this audio file
   * @author Joseph
   */
  void incVolume() {
    float prev = gainControl.getValue();
    gainControl.setValue(Math.min(prev + 0.86f, gainControl.getMaximum()));
    previousVolume = gainControl.getValue();
  }

  /**
   * Method to decrease the volume of this audio file
   * @author Joseph
   */
  void decVolume() {
    float prev = gainControl.getValue();
    gainControl.setValue(Math.max(prev - 0.86f, gainControl.getMinimum()));
    previousVolume = gainControl.getValue();
  }

  /**
   * Method to get whether or not this audio file is currently playing
   * @return Whether or not this audio file is currently playing
   * @author Adam W
   */
  boolean isPlaying() {
    return this.clip.isActive();
  }

  /**
   * Method to reset the audio stream so that this sound can be played again if necessary
   * @throws UnsupportedAudioFileException When this audio file is of the wrong type
   * @throws IOException When this audio file cannot be found
   * @throws LineUnavailableException When the line this audio file uses is already in use
   * @author Adam W
   */
  private void resetStream()
      throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.source = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
    this.clip.open(this.source);
    gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(previousVolume);
    this.clip.stop();
    if (this.loops) {
      this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }
}
