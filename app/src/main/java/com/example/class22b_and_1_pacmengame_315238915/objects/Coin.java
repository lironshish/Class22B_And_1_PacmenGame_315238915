package com.example.class22b_and_1_pacmengame_315238915.objects;

public class Coin {
    private int coin_x;
    private int coin_y;

    public Coin(int x, int y){
        setCoin_x(x);
        setCoin_y(y);
    }

    public int getCoin_x() {
        return coin_x;
    }

    public void setCoin_x(int coin_x) {
        this.coin_x = coin_x;
    }

    public int getCoin_y() {
        return coin_y;
    }

    public void setCoin_y(int coin_y) {
        this.coin_y = coin_y;
    }
}
