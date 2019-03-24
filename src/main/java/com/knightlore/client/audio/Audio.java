package com.knightlore.client.audio;

public class Audio {

  private static final String AUDIO_PATH = "src/main/resources/audio/";

  private static MusicPlayer menuMusic;
  private static MusicPlayer gameMusic;
  private static MusicPlayer editorMusic;
  private static MusicPlayer menuSelectSound;
  private static MusicPlayer hitSound;
  private static MusicPlayer deathSound;
  private static MusicPlayer rollSound;
  private static MusicPlayer climbSound;
  private static MusicPlayer victoryJingle;
  private static MusicPlayer gameOverJingle;
  private static MusicPlayer[] audioFiles;
  private static boolean isOn;
  
  private static int currentMusic;

  private Audio() {}

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

    audioFiles =
        new MusicPlayer[] {
          menuMusic,
          gameMusic,
          editorMusic,
          menuSelectSound,
          hitSound,
          deathSound,
          rollSound,
          victoryJingle,
          gameOverJingle
        };
    
    currentMusic = 0;
  }

  public static boolean isOn() {
    return isOn;
  }

  // param1: the audio file to be played
  public static void play(AudioName file) {
    if (isOn) {
      try {
        audioFiles[file.ordinal()].play();
        currentMusic = file.ordinal();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void stop(AudioName file) {
	  if (audioFiles[file.ordinal()].isPlaying()) {
		  audioFiles[file.ordinal()].stop();
	  }
  }

  // Closes any audio files that have finished playing so that they can be played again
  public static void closeInactiveClips() {
    for (MusicPlayer audioFile : audioFiles) {
      if (!audioFile.isPlaying()) {
        audioFile.stop();
      }
    }
  }

  public static void toggle() {
    isOn = !isOn;
    if (!isOn) {
      stop(AudioName.values()[currentMusic]);
    } else {
      play(AudioName.values()[currentMusic]);
    }
  }

  public static void restart() {
    if (isOn) {
      toggle();
      toggle();
    }
  }

  public static void incVolume() {
    gameMusic.incVolume();
  }

  public static void decVolume() {
    gameMusic.decVolume();
  }

  public enum AudioName {
    MUSIC_MENU,
    MUSIC_GAME,
    MUSIC_EDITOR,
    SOUND_MENUSELECT,
    SOUND_HIT,
    SOUND_DEATH,
    SOUND_ROLL,
    SOUND_CLIMB,
    JINGLE_VICTORY,
    JINGLE_GAMEOVER
  }
}
