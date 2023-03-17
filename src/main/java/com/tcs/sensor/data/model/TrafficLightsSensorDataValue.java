package com.tcs.sensor.data.model;

import java.util.Random;

public enum TrafficLightsSensorDataValue {
    GREEN, RED;

    private static final Random RANDOM = new Random();

    public static TrafficLightsSensorDataValue randomValue()  {
        TrafficLightsSensorDataValue[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }
}
