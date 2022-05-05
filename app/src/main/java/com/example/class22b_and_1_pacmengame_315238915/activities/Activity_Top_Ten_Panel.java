package com.example.class22b_and_1_pacmengame_315238915.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class22b_and_1_pacmengame_315238915.R;
import com.example.class22b_and_1_pacmengame_315238915.fragments.Fragment_List;
import com.example.class22b_and_1_pacmengame_315238915.fragments.Fragment_Map;

public class Activity_Top_Ten_Panel extends AppCompatActivity {

    private TextView top_ten_LBL_title;
    private Button TopTen_BTN_PlayAgain;
    private TextView top_ten_LBL_map;

    Fragment_Map fragmentMap;
    Fragment_List fragmentList;

    private String fromMenu;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            fromMenu = bundle.getString("fromMenu");
        } else {
            this.bundle = new Bundle();
        }
        findViews();
        InitBtnClick();
        //Init Fragments
         fragmentMap = new Fragment_Map();
         fragmentList = new Fragment_List();

        //Open Fragments
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragmentMap)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_LAY_list, fragmentList)
                .commit();

        //Set callBacks
        fragmentList.setActivity(this);
        fragmentList.setCallBackList((lat, lon, playerName) -> fragmentMap.locateOnMap(lat,lon));


    }

    private void findViews() {
        top_ten_LBL_title = findViewById(R.id.game_top_ten_LBL_title);
        top_ten_LBL_map = findViewById(R.id.game_top_ten_LBL_map);
        TopTen_BTN_PlayAgain = findViewById(R.id.TopTen_BTN_PlayAgain);
        if(fromMenu.equals("fromMenu"))
            TopTen_BTN_PlayAgain.setVisibility(View.INVISIBLE);
    }

    public void InitBtnClick() {
        TopTen_BTN_PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaccActivity();
            }
        });
    }

    public void replaccActivity(){
        Intent intent = new Intent(this, Activity_Panel.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }
}