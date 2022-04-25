package com.example.class22b_and_1_pacmengame_315238915;

import android.os.Handler;

public class Step_Detector {

    private int stepCount = 0;

    public Step_Detector() { }
    public void start(){
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepCount++;
                handler.postDelayed(this,delay);
            }
        } , delay);
    }
    public int getStepCount(){
        return stepCount;
    }
}