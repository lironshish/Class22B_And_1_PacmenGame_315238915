package com.example.class22b_and_1_pacmengame_315238915.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.class22b_and_1_pacmengame_315238915.Game_Manager;
import com.example.class22b_and_1_pacmengame_315238915.R;

public class Activity_Menu extends AppCompatActivity {

    private ImageView panel_IMG_game_menu_background;
    private Button menu_BTN_Sensor_game;
    private Button menu_BTN_buttons_game;
    private Button menu_BTN_records_list;
    private Button menu_BTN_login;
    private EditText menu_LBL_user_name;

    private Bundle bundle;
    //User details
    private String userName;

    private Game_Manager game_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        game_manager = new Game_Manager();

        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            game_manager.setUserName(bundle.getString("playerName"));
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        InitBtnClick();
    }

    public void findViews() {
        panel_IMG_game_menu_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_Sensor_game = findViewById(R.id.menu_BTN_sensor_game);
        menu_BTN_buttons_game = findViewById(R.id.menu_BTN_buttons_game);
        menu_BTN_records_list = findViewById(R.id.menu_BTN_Records_list);
        menu_LBL_user_name = findViewById(R.id.menu_LBL_name);
        menu_BTN_login = findViewById(R.id.menu_BTN_login);

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void InitBtnClick() {
        menu_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
                userName = menu_LBL_user_name.getText().toString();
                game_manager.setUserName(menu_LBL_user_name.getText().toString());
                menu_BTN_Sensor_game.setVisibility(View.VISIBLE);
                menu_BTN_buttons_game.setVisibility(View.VISIBLE);
                menu_BTN_records_list.setVisibility(View.VISIBLE);
                menu_LBL_user_name.setVisibility(View.INVISIBLE);
                menu_BTN_login.setVisibility(View.INVISIBLE);
            }
        });

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
            }
        });

        menu_BTN_records_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextRecActivity();
         }
        });

    }

    public void nextRecActivity(){
        Intent intent = new Intent(this, Activity_Top_Ten_Panel.class);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void replaceActivity(String game) {
        Intent intent = new Intent(this, Activity_Panel.class);
        Bundle bundle = new Bundle();
        bundle.putString("playerName",userName);
        bundle.putString("game",game);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }

}
