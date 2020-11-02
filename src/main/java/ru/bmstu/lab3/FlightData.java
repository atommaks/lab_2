package ru.bmstu.lab3;

import java.io.Serializable;

public class FlightData implements Serializable {
    private float delay;
    private int abortedFlightCount, delayedFlightCount, flightCount;

    public FlightData(float delay, boolean aborted) {
        flightCount = 1;
        if (aborted) {
            this.delay = 0.0f;
            abortedFlightCount = 1;
            delayedFlightCount = 0;
        } else {
            if (delay != 0.0f) {
                delayedFlightCount = 1;
                abortedFlightCount = 0;
                this.delay = delay;
            } else {
                delayedFlightCount = 0;
                abortedFlightCount = 0;
                this.delay = 0.0f;
            }
        }
    }
}
