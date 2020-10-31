package ru.bmstu.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class IdPartitioner extends Partitioner<IDKey, Text> {
    @Override
    public int getPartition(IDKey key, Text value, int numPartitions) {
        return (key.getAirportId().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}