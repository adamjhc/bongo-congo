package com.knightlore.client.util.audio;

public class AudioHandler {

    public static AudioHandler handler = new AudioHandler();

    private boolean playingMusic = false;

    public void playSound(){

    }

    public void startMusic(){
        playingMusic = true;
    }

    public void stopMusic(){
        playingMusic = false;
    }

    public void setMusicVolume(){

    }

    public void toggleMusic(){
        if(this.playingMusic){
            startMusic();
        }else{
            stopMusic();
        }
    }
}
