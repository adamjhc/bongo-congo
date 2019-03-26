package com.knightlore.client.audio;

public class Audio {

  /**
   * The file path to all the audio files
   */
  private static final String AUDIO_PATH = "src/main/resources/audio/";

  /**
   * The MusicPlayer for the menu music
   */
  private static MusicPlayer menuMusic;
  
  /**
   * The MusicPlayer for the main game music
   */
  private static MusicPlayer gameMusic;
  
  /**
   * The MusicPlayer for the level editor music
   */
  private static MusicPlayer editorMusic;
  
  /**
   * The sound effect for selecting a menu item
   */
  private static MusicPlayer menuSelectSound;

  /**
   * The sound effect for dying
   */
  private static MusicPlayer hitSound;
  
  /**
   * The sound effect for rolling
   */
  private static MusicPlayer rollSound;
  
  /**
   * The sound effect for climbing
   */
  private static MusicPlayer climbSound;
  
  /**
   * The jingle that plays when you win
   */
  private static MusicPlayer victoryJingle;
  
  /**
   * The jingle that plays when you lose
   */
  private static MusicPlayer gameOverJingle;
  
  /**
   * The list of all the audio files
   */
  private static MusicPlayer[] audioFiles;
  
  /**
   * Whether music has been muted or not
   */
  private static boolean isOn;
  
  /**
   * The index in audioFiles of the current music being played
   */
  private static int currentMusic;

  private Audio() {}

  /**
   * Method to retrieve all the audio files and choose the initial music
   */
  public static void init() {
    try {
      menuMusic = 		new MusicPlayer(AUDIO_PATH + "MUSIC_MENU.wav", true);
    } catch (Exception e) {
      System.err.println("Could not load menu music: " + e);
    }
    
    try {
      gameMusic = new MusicPlayer((AUDIO_PATH + "MUSIC_GAME.wav"), true);
    } catch (Exception e) {
      System.err.println("Could not load game music: " + e);
    }
    
    try {
      editorMusic = 		new MusicPlayer(AUDIO_PATH + "MUSIC_EDITOR.wav", true);
    } catch (Exception e) {
      System.err.println("Could not load endgame music: " + e);
    }

    try {
      menuSelectSound = 	new MusicPlayer(AUDIO_PATH + "SOUND_MENUSELECT.wav", false);
    } catch (Exception e) {
      System.err.println("Could not load menu select sound: " + e);
    }

    try {
       hitSound = 			new MusicPlayer(AUDIO_PATH + "SOUND_HIT.wav", false);
    } catch (Exception e) {
       System.err.println("Could not load hit sound: " + e);
    }
    
    
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
    try {
       victoryJingle = 	new MusicPlayer(AUDIO_PATH + "JINGLE_VICTORY.wav", false);
    } catch (Exception e) {
       System.err.println("Could not load victory jingle: " + e);
    }

    try {
       gameOverJingle = 	new MusicPlayer(AUDIO_PATH + "JINGLE_GAMEOVER.wav", false);
    } catch (Exception e) {
       System.err.println("Could not load game over jingle: " + e);
    }
     

    audioFiles =
        new MusicPlayer[] {
          menuMusic,
          gameMusic,
          editorMusic,
          menuSelectSound,
          hitSound,
          //rollSound,
          victoryJingle,
          gameOverJingle
        };
    
    currentMusic = 0;
  }

  /**
   * Method to get whether music has been muted or not
   * @return Whether music has been muted or not
   */
  public static boolean isOn() {
    return isOn;
  }
  
  /**
   * Method to get the current music being played
   * @return The MusicPlayer for the current music being played
   */
  public static AudioName getCurrentMusic() {
	  return AudioName.values()[currentMusic];
  }

  /**
   * Method to play a given audio file
   * @param file The name of the file to be played
   */
  public static void play(AudioName file) {
    if (isOn) {
      try {
        audioFiles[file.ordinal()].play();
        if (file.ordinal() < 3)
        	currentMusic = file.ordinal();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * Method to stop a given audio file
   * @param file The name of the file to be played
   */
  public static void stop(AudioName file) {
	  if (audioFiles[file.ordinal()].isPlaying()) {
		  audioFiles[file.ordinal()].stop();
	  }
  }

  /**
   * Method to close any unused audio clips so that they can be played again
   */
  public static void closeInactiveClips() {
    for (MusicPlayer audioFile : audioFiles) {
      if (!audioFile.isPlaying() && audioFile != audioFiles[currentMusic]) {
        audioFile.stop();
      }
    }
  }

  /**
   * Method to toggle whether music is muted or not
   */
  public static void toggle() {
    isOn = !isOn;
    if (!isOn) {
      stop(AudioName.values()[currentMusic]);
    } else {
      play(AudioName.values()[currentMusic]);
    }
  }

  /**
   * Method to restart the current music
   */
  public static void restart() {
    if (isOn) {
      toggle();
      toggle();
    }
  }

  /**
   * Method to increment the volume for all audio files
   */
  public static void incVolume() {
	for (MusicPlayer audioFile : audioFiles) {
		audioFile.incVolume();
	}
  }

  /**
   * Method to decrement the volume for all audio files
   */
  public static void decVolume() {
	for (MusicPlayer audioFile : audioFiles) {
		audioFile.decVolume();
	}
  }

  /**
   * The names of all the audio files in use
   * @author GreenMario
   *
   */
  public enum AudioName {
    MUSIC_MENU,
    MUSIC_GAME,
    MUSIC_EDITOR,
    SOUND_MENUSELECT,
    SOUND_HIT,
    //SOUND_ROLL,
    //SOUND_CLIMB,
    JINGLE_VICTORY,
    JINGLE_GAMEOVER
  }
}
