package com.example.class22b_and_1_pacmengame_315238915;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity_Menu extends AppCompatActivity {

    private ImageView panel_IMG_game_menu_background;
    private Button menu_BTN_Sensor_game;
    private Button menu_BTN_buttons_game;
    private Button menu_BTN_records_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        findViews();
        InitBtnClick();
    }

    public void findViews() {
        panel_IMG_game_menu_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_Sensor_game = findViewById(R.id.menu_BTN_Sensor_game);
        menu_BTN_buttons_game = findViewById(R.id.menu_BTN_buttons_game);
        menu_BTN_records_list = findViewById(R.id.menu_BTN_Records_list);
    }


    public void InitBtnClick() {
//        menu_BTN_Sensor_game.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nextActivity(v);
//            }
//        });


        menu_BTN_buttons_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

    }


    public void nextActivity(){
        Intent game_page = new Intent(this, Activity_Panel.class);
        startActivity(game_page);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
