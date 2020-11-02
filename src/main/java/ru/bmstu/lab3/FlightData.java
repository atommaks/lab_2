package ru.bmstu.lab3;

import java.io.Serializable;

public class FlightData implements Serializable {
    private float delay;
    private int abortedFlightCount, delayedFlightCount, flightCount;

    public FlightData(float delay, boolean aborted) {
        flightCount = 1;
        
    }
}
