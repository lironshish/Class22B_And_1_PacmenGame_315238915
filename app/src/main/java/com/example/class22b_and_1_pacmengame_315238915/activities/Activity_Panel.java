package com.example.class22b_and_1_pacmengame_315238915.activities;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.class22b_and_1_pacmengame_315238915.Game_Manager;
import com.example.class22b_and_1_pacmengame_315238915.Location_Manager;
import com.example.class22b_and_1_pacmengame_315238915.MSPV3;
import com.example.class22b_and_1_pacmengame_315238915.R;
import com.example.class22b_and_1_pacmengame_315238915.Sensors;
import com.example.class22b_and_1_pacmengame_315238915.objects.Coin;
import com.example.class22b_and_1_pacmengame_315238915.objects.MyDB;
import com.example.class22b_and_1_pacmengame_315238915.objects.Player;
import com.example.class22b_and_1_pacmengame_315238915.objects.Record;
import com.example.class22b_and_1_pacmengame_315238915.objects.Sounds;
import com.example.class22b_and_1_pacmengame_315238915.objects.Step_Detector;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_Panel extends AppCompatActivity {
    //Final Variables
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int PLAYER_START_POS_X = 6;
    public static final int PLAYER_START_POS_Y = 2;
    public static final int RIVAL_START_POS_X = 0;
    public static final int RIVAL_START_POS_Y = 0;


    public static int PLAYER_DIRECTION = -1;
    public static int RIVAL_DIRECTION = -1;

    private String game;
    private Bundle bundle;
    //Display Background
    private ImageView panel_IMG_background;
    //Display game over message
    private ImageView panel_IMG_game_over;


    //Display Snails that simulate life
    private ImageView[] panel_IMG_snails;
    private int lives;

    //Display Arrows
    private ImageButton[] panel_IMG_arrows;

    //Game Manager
    private final Game_Manager gameManager = new Game_Manager();

    //Panel
    private ImageView[][] panelGame;

    //Timer
    private Timer timer = new Timer();
    private final int DELAY = 1000;
    private int counter = 0;
    private MaterialTextView main_LBL_time;
    private enum TIMER_STATUS{
        OFF,
        RUNNING,
        PAUSE
    }
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;

    //Players
    private Player player;
    private Player rival;
    private Coin coin;

    //Step Detector
    private Step_Detector stepDetector;

    //Sounds
    private Sounds sound;

    //Sensors
    private Sensors sensors;
    private SensorManager sensorManager;
    private int sensorFlag = 0;

    Location_Manager locationManager;
    private final MyDB myDB = MyDB.initMyDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            gameManager.setUserName(bundle.getString("playerName"));
        } else {
            this.bundle = new Bundle();
        }
        game = bundle.getString("game");
        if(game.equals("buttons")){
            setContentView(R.layout.activity_buttons_game);
            InitGameView();
            InitArrowsButtons();
        }else{
            setContentView(R.layout.activity_sensor_game);
            sensorFlag = 1;
            InitGameView();
            sensors = new Sensors();
            initSensors();
        }
        sound = new Sounds();
        locationManager = new Location_Manager(this);
        //----- Get Location use permission and check -----
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Init Functions
    private void InitGameView() {
        //Background
        panel_IMG_background = findViewById(R.id.panel_IMG_background);

        //Game over
        panel_IMG_game_over = findViewById(R.id.panel_IMG_game_over);

        //Timer
        main_LBL_time = findViewById(R.id.main_LBL_time);

        //Panel
        panelGame = new ImageView[][]{
                {findViewById(R.id.panel_IMG_00),findViewById(R.id.panel_IMG_01),findViewById(R.id.panel_IMG_02),findViewById(R.id.panel_IMG_03),findViewById(R.id.panel_IMG_04)},
                {findViewById(R.id.panel_IMG_10),findViewById(R.id.panel_IMG_11),findViewById(R.id.panel_IMG_12),findViewById(R.id.panel_IMG_13),findViewById(R.id.panel_IMG_14)},
                {findViewById(R.id.panel_IMG_20),findViewById(R.id.panel_IMG_21),findViewById(R.id.panel_IMG_22),findViewById(R.id.panel_IMG_23),findViewById(R.id.panel_IMG_24)},
                {findViewById(R.id.panel_IMG_30),findViewById(R.id.panel_IMG_31),findViewById(R.id.panel_IMG_32),findViewById(R.id.panel_IMG_33),findViewById(R.id.panel_IMG_34)},
                {findViewById(R.id.panel_IMG_40),findViewById(R.id.panel_IMG_41),findViewById(R.id.panel_IMG_42),findViewById(R.id.panel_IMG_43),findViewById(R.id.panel_IMG_44)},
                {findViewById(R.id.panel_IMG_50),findViewById(R.id.panel_IMG_51),findViewById(R.id.panel_IMG_52),findViewById(R.id.panel_IMG_53),findViewById(R.id.panel_IMG_54)},
                {findViewById(R.id.panel_IMG_60),findViewById(R.id.panel_IMG_61),findViewById(R.id.panel_IMG_62),findViewById(R.id.panel_IMG_63),findViewById(R.id.panel_IMG_64)}
        };

        //Snails
        panel_IMG_snails = new ImageView[] {
                findViewById(R.id.panel_IMG_snail1),
                findViewById(R.id.panel_IMG_snail2),
                findViewById(R.id.panel_IMG_snail3)
        };
        lives = gameManager.getMaxLives();

        //Players
        player = new Player(PLAYER_START_POS_X, PLAYER_START_POS_Y,PLAYER_DIRECTION);
        rival = new Player(RIVAL_START_POS_X, RIVAL_START_POS_Y,RIVAL_DIRECTION);
        coin = new Coin(1,1);// default

        //Player
        panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
        panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);

        //Rival
        panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
        panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);

        //Step Detector
        stepDetector = new Step_Detector();
        stepDetector.start();
    }

    private void InitArrowsButtons() {
        //Arrows
        panel_IMG_arrows = new ImageButton[] {
                findViewById(R.id.panel_BTN_left),
                findViewById(R.id.panel_BTN_up),
                findViewById(R.id.panel_BTN_down),
                findViewById(R.id.panel_BTN_right)
        };
        //Each time the player changes the direction of his movement, the rival also changes the direction of his movement randomly
        //Left
        panel_IMG_arrows[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = LEFT;
            }
        });

        //Up
        panel_IMG_arrows[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = UP;
            }
        });

        //Down
        panel_IMG_arrows[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = DOWN;
            }
        });

        //Right
        panel_IMG_arrows[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = RIGHT;
            }
        });
    }


    //Sensors Functions
   public void initSensors(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors.setSensorManager(sensorManager);
        sensors.initSensor();
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];

            if (x < -5) {// move right
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = RIGHT;
            } else if (x > 5) {// move left
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = LEFT;
            } else if (y < -3) {// move up
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = UP;
            } else if (y > 5) {// move down
                RIVAL_DIRECTION = getRandomRivalDirection();
                PLAYER_DIRECTION = DOWN;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    protected void onResume() {
        super.onResume();
        if(sensorFlag == 1) {
            sensorManager.registerListener(accSensorEventListener, sensors.getAccSensor(), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorFlag == 1) {
            sensorManager.unregisterListener(accSensorEventListener);
        }
    }


    //Timer Functions
    @Override
    protected void onStart() {
        super.onStart();
        if(timerStatus == TIMER_STATUS.OFF){
            startTimer();
        } else if(timerStatus == TIMER_STATUS.RUNNING ){
            stopTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tick();
                        runLogic();
                    }
                });

            }
        }, 0, DELAY);

    }
    private void tick() {
        ++counter;
        main_LBL_time.setText("" + counter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timerStatus == TIMER_STATUS.RUNNING){
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    private void stopTimer() {
        timerStatus = TIMER_STATUS.OFF;
        timer.cancel();

    }

    //Logic Functions
    public void runLogic(){
        moveRival();
        moveFish();
        if(stepDetector.getStepCount()%10==0){
            AddRandomCoin();
        }
        updateScore();
        checkIfCrash();

    }

    //Move Functions
    private void moveFish() {
        if(lives == 0)
            panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
        switch (PLAYER_DIRECTION){
            case LEFT:
                panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
                if(player.getY() == 0)
                    player.setY(gameManager.getCOLUMNS() -1);
                else
                    player.setY(player.getY()-1);
                panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
                panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);
                break;
            case UP:
                panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
                if(player.getX() == 0)
                    player.setX(gameManager.getROWS()-1);
                else
                    player.setX(player.getX()-1);
                panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
                panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);
                break;
            case DOWN:
                panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
                if(player.getX() == (gameManager.getROWS() - 1))
                    player.setX(0);
                else
                    player.setX(player.getX()+1);
                panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
                panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);
                break;
            case RIGHT:
                panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
                if(player.getY() == (gameManager.getCOLUMNS() - 1))
                    player.setY(0);
                else
                    player.setY(player.getY()+1);
                panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
                panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);
                break;
        }

    }

    private void moveRival() {
        if(lives == 0)
            panelGame[rival.getX()][rival.getY()].setVisibility(View.INVISIBLE);
        switch (RIVAL_DIRECTION){
            case LEFT:
                panelGame[rival.getX()][rival.getY()].setVisibility(View.INVISIBLE);
                if(rival.getY() == 0)
                    rival.setY(gameManager.getCOLUMNS() - 1);
                else
                    rival.setY(rival.getY()-1);
                panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
                panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);
                break;
            case UP:
                panelGame[rival.getX()][rival.getY()].setVisibility(View.INVISIBLE);
                if(rival.getX() == 0)
                    rival.setX(gameManager.getROWS()-1);
                else
                    rival.setX(rival.getX()-1);
                panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
                panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);
                break;
            case DOWN:
                panelGame[rival.getX()][rival.getY()].setVisibility(View.INVISIBLE);
                if(rival.getX() == (gameManager.getROWS() - 1))
                    rival.setX(0);
                else
                    rival.setX(rival.getX()+1);
                panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
                panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);
                break;
            case RIGHT:
                panelGame[rival.getX()][rival.getY()].setVisibility(View.INVISIBLE);
                if(rival.getY() == (gameManager.getCOLUMNS() - 1))
                    rival.setY(0);
                else
                    rival.setY(rival.getY()+1);
                panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
                panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);
                break;
        }

    }
    // Rival functions
    private int getRandomRivalDirection(){
        RIVAL_DIRECTION = (int) (Math.random() * 4); // 4 directions - up, down, right and left
        return RIVAL_DIRECTION;
    }


    //Loss function
    private void checkIfCrash()
    {
        if((rival.getX() == player.getX()) && (rival.getY() == player.getY()))
        {
            panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
            sound.setMpAndPlay((ContextWrapper) getApplicationContext(),R.raw.sound_crash);
            if(gameManager.getLives()>1) {
                gameManager.reduceLives();
                panel_IMG_snails[gameManager.getLives()].setVisibility(View.INVISIBLE);
                Toast.makeText(this,"BOOM",Toast.LENGTH_SHORT).show();
                //Returning the players to the starting point
                setPlayersOnStartingPoint();


            } else {
                panel_IMG_snails[0].setVisibility(View.INVISIBLE);
                stopTimer();
                sound.setMpAndPlay((ContextWrapper) getApplicationContext(),R.raw.sound_game_over);
                panelGame[player.getX()][player.getY()].setVisibility(View.INVISIBLE);
                Toast.makeText(this,"Game Over",Toast.LENGTH_SHORT).show();
                panel_IMG_game_over.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Record rec = new Record()
                                .setName(gameManager.getUserName())
                                .setScore(counter)
                                .setLon(locationManager.getLon())
                                .setLat(locationManager.getLat());

                        myDB.addRecord(rec);
                        String json = new Gson().toJson(myDB);
                        MSPV3.getMe().putString("MY_DB", json);
                        replaceActivity();
                        finish();
                    }
                }, 500);
            }
        }

    }
    private void replaceActivity() {
        Intent intent = new Intent(this, Activity_Top_Ten_Panel.class);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }


    private void setPlayersOnStartingPoint(){

        player.setX(PLAYER_START_POS_X);
        player.setY(PLAYER_START_POS_Y);
        rival.setX(RIVAL_START_POS_X);
        rival.setY(RIVAL_START_POS_Y);

        panelGame[rival.getX()][rival.getY()].setImageResource(R.drawable.ic_shark);
        panelGame[rival.getX()][rival.getY()].setVisibility(View.VISIBLE);

        panelGame[player.getX()][player.getY()].setImageResource(R.drawable.ic_fish);
        panelGame[player.getX()][player.getY()].setVisibility(View.VISIBLE);

    }


    // Random Coin
    public void AddRandomCoin()
    {
        panelGame[coin.getCoin_x()][coin.getCoin_y()].setVisibility(View.INVISIBLE);
        do {
            coin.setCoin_x((int) (Math.random() * gameManager.getROWS()));
            coin.setCoin_y((int) (Math.random() * gameManager.getCOLUMNS()));
        } while(coin.getCoin_x()!= player.getX() && coin.getCoin_x()!= rival.getX() && coin.getCoin_y()!= rival.getY()&& coin.getCoin_y()!= player.getY());
        panelGame[coin.getCoin_x()][coin.getCoin_y()].setImageResource(R.drawable.ic_starfish);
        panelGame[coin.getCoin_x()][coin.getCoin_y()].setVisibility(View.VISIBLE);
    }



 private void updateScore() {
     if ((coin.getCoin_x() == player.getX()) && (coin.getCoin_y() == player.getY())){
         sound.setMpAndPlay((ContextWrapper) getApplicationContext(),R.raw.sound_player_hits_coin);
         counter += 50;
         main_LBL_time.setText("" + counter);
         Toast.makeText(this,"+50",Toast.LENGTH_SHORT).show();
         stepDetector.setStepCount(0);
     }
     if ((coin.getCoin_x() == rival.getX()) && (coin.getCoin_y() == rival.getY())){
         sound.setMpAndPlay((ContextWrapper) getApplicationContext(),R.raw.sound_rival_hits_coin);
         if(counter<50)
             counter = 0;
         else
             counter-=50;
         Toast.makeText(this,"Oh No...",Toast.LENGTH_SHORT).show();
         stepDetector.setStepCount(0);
     }

    }


}



