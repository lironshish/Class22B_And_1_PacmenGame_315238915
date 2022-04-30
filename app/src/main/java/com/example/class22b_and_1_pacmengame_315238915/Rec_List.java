package com.example.class22b_and_1_pacmengame_315238915;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Rec_List extends AppCompatActivity {

//    private ImageView rec_list_IMG_background;
//    private TextView rec_list_LBL_Welcome;
    private TextView top_ten_LBL_title;
    private Button top_ten_BTN_back;
    private TextView top_ten_LBL_map;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);
        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
        } else {
            this.bundle = new Bundle();
        }
        findViews();
        //Init Fragments
        Fragment fragment = new Fragment_Map();

        //Open Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }



private void findViews() {
    top_ten_LBL_title = findViewById(R.id.game_top_ten_LBL_title);
    top_ten_LBL_map = findViewById(R.id.game_top_ten_LBL_map);
}

}