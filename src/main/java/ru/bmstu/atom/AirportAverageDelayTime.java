package ru.bmstu.atom;

public class AirportAverageDelayTime {

    public abstract class Partitioner<KEY, VALUE> {
        public abstract int getPartition(KEY key, VALUE value, int numPartitions);
    }
}

