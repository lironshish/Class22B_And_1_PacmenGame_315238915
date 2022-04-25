package com.example.class22b_and_1_pacmengame_315238915;

public class Game_Manager {

    //Final variables
    public static final int MAX_LIVES = 3;
    public static final int COLUMNS = 5;
    public static final int ROWS = 7;

    private int lives = MAX_LIVES;

    //Default Constructor
    public Game_Manager() {

    }

    //Setters and Getters
    public int getLives() {
        return lives;
    }

    public void reduceLives() {
        lives--;
    }

    public static int getROWS() {
        return ROWS;
    }

    public static int getCOLUMNS() {
        return COLUMNS;
    }

    public static int getMaxLives() { return MAX_LIVES;}

}
