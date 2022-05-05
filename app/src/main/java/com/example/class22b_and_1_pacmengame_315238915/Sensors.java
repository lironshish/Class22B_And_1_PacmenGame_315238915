package com.example.class22b_and_1_pacmengame_315238915;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class Sensors {

    private SensorManager sensorManager;
    private Sensor accSensor;

    public Sensors() {
    }

    public Sensors setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        return this;
    }

    public Sensor getAccSensor() {
        return accSensor;
    }

    public void initSensor() {
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

}