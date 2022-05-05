package com.example.class22b_and_1_pacmengame_315238915.objects;

public class Record implements Comparable {
    private int score = 0;
    private String name = "";
    private double lat = 0.0;
    private double lon = 0.0;

    public Record() {
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        Record other = (Record) o;
        if (this.score> other.score)
            return -1;
        return 1;
    }
}
