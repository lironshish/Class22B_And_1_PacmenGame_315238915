package com.example.class22b_and_1_pacmengame_315238915;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Activity_Menu extends AppCompatActivity {

    private ImageView panel_IMG_game_menu_background;
    private Button menu_BTN_Sensor_game;
    private Button menu_BTN_buttons_game;
    private Button menu_BTN_records_list;
    private EditText menu_LBL_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        InitBtnClick();
    }

    public void findViews() {
        panel_IMG_game_menu_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_Sensor_game = findViewById(R.id.menu_BTN_sensor_game);
        menu_BTN_buttons_game = findViewById(R.id.menu_BTN_buttons_game);
        menu_BTN_records_list = findViewById(R.id.menu_BTN_Records_list);
        menu_LBL_user_name = findViewById(R.id.menu_LBL_name);
    }


    public void InitBtnClick() {
        menu_BTN_Sensor_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity("sensors");
            }
        });


        menu_BTN_buttons_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity("buttons");
              //  nextActivity();
            }
        });

    }


//    public void nextActivity(){
//        Intent game_page = new Intent(this, Activity_Panel.class);
//        startActivity(game_page);
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void replaceActivity(String game) {
        Intent intent = new Intent(this,Activity_Panel.class);
        Bundle bundle = new Bundle();
        //bundle.putString("playerName",playerName);
        bundle.putString("game",game);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }

}
