package com.example.class22b_and_1_pacmengame_315238915;

import android.app.Application;

import com.example.class22b_and_1_pacmengame_315238915.Utils.MSPV3;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSPV3.initHelper(this);
    }
}
