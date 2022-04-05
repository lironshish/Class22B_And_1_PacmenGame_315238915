package com.example.class22b_and_1_pacmengame_315238915;

public class Player {

    private int x;
    private int y;
    private int direction;

    //Default constructor
    public Player() { }

    public Player(int x, int y,int direction) {
        setX(x);
        setY(y);
        setDirection(direction);
    }

    //Setters and Getters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
