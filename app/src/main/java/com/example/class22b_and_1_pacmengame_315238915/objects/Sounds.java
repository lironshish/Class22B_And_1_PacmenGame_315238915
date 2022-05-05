package com.example.class22b_and_1_pacmengame_315238915.objects;

import android.content.ContextWrapper;
import android.media.MediaPlayer;

public class Sounds {

    private MediaPlayer mp;

    public Sounds() {
        MediaPlayer mp = new MediaPlayer();
    }

    public void setMpAndPlay(ContextWrapper cw, int sample) {
        this.mp = MediaPlayer.create(cw,sample);
        this.mp.start();
    }
}
